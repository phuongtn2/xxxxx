package vn.azteam.tabasupport.core.cache.impl;

import org.springframework.stereotype.Component;
import vn.azteam.tabasupport.core.cache.ResetPasswordTokenCache;
import vn.azteam.tabasupport.core.cache.object.CacheObject;
import vn.azteam.tabasupport.core.object.model.Account;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

/**
 * package vn.azteam.tabasupport.core.cache.impl
 * created 12/27/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @see ResetPasswordTokenCache
 * @since 1.0.0
 */
@Component
public class ResetPasswordTokenCacheImpl implements ResetPasswordTokenCache {
	private static final long expireInterval = 86400000L;
	private static final long cleanningMinInterval = 600000L;
	private final HashMap<UUID, CacheObject> cacheMap = new HashMap<UUID, CacheObject>();
	private long previsous = System.currentTimeMillis();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String cache(Account account) {
		final UUID uuid = UUID.randomUUID();
		final CacheObject object = new CacheObject(account, System.currentTimeMillis());
		synchronized (cacheMap) {
			for (final Map.Entry<UUID, CacheObject> em : cacheMap.entrySet()) {
				final CacheObject cacheObject = em.getValue();
				final UUID key = em.getKey();
				if (cacheObject.getObject().equals(account)) {
					cacheMap.remove(key, cacheObject);
				}
			}

			cacheMap.put(uuid, object);
		}
		return uuid.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Account getCached(String uid) {
		final long now = System.currentTimeMillis();
		synchronized (cacheMap) {
			clean(now);
			for (final Map.Entry<UUID, CacheObject> em : cacheMap.entrySet()) {
				final UUID uuid = em.getKey();
				if (uuid.toString().equals(uid)) {
					return (Account) em.getValue().getObject();
				}
			}
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void remove(String uid) {
		final long now = System.currentTimeMillis();
		synchronized (cacheMap) {
			clean(now);
			for (final Map.Entry<UUID, CacheObject> em : cacheMap.entrySet()) {
				final UUID uuid = em.getKey();
				if (uuid.toString().equals(uid)) {
					cacheMap.remove(uuid);
				}
			}
		}
	}

	@Override
	public void clean() {
		final long now = System.currentTimeMillis();
		clean(now);
	}

	private void clean(long now) {
		if (now > previsous + cleanningMinInterval) {
			previsous = now;
			final long at = now - expireInterval;
			final LinkedList<UUID> removeKeyList = new LinkedList<UUID>();
			for (final Map.Entry<UUID, CacheObject> em : cacheMap.entrySet()) {
				final CacheObject object = em.getValue();
				if (object.getCacheAt() <= at) {
					removeKeyList.add(em.getKey());
				}
			}
			for (final UUID key : removeKeyList) {
				cacheMap.remove(key);
			}
		}
	}
}
