package vn.azteam.tabasupport.modules.material.rest.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import vn.azteam.tabasupport.core.object.exception.ValidationException;
import vn.azteam.tabasupport.core.rest.impl.BaseRestImpl;
import vn.azteam.tabasupport.modules.material.object.model.Material;
import vn.azteam.tabasupport.modules.material.rest.MaterialApi;
import vn.azteam.tabasupport.modules.material.service.MaterialService;
import vn.azteam.tabasupport.util.MapBeanConverter;
import vn.azteam.tabasupport.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * package vn.azteam.tabasupport.modules.material.rest.impl
 * created 12/15/2016.
 *
 * @author hieunc.
 * @version 1.0.0
 * @link http://azteam.vn
 * @since 1.0.0
 */
@Component
public class MaterialApiImpl extends BaseRestImpl implements MaterialApi {

    @Autowired
    private MaterialService materialService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getAllMaterial(HttpServletRequest request, OAuth2Authentication auth) {
        final Map<String, String[]> paramsMapper = request.getParameterMap();
        List<Material> materials = materialService.getAllMaterials();

        List<Material> materialFilter = new ArrayList<>();
        if (paramsMapper.containsKey("subType")) {
            String subType = request.getParameter("subType");
            if (!StringUtil.isEmpty(subType)) {
                materialFilter = materials.stream().filter(
                        material -> material.getSubType().equalsIgnoreCase(subType)
                ).collect(Collectors.toList());
            }
            return materialFilter;
        }

        return materials;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Material getMaterialById(OAuth2Authentication auth, @PathVariable("materialId") long materialId) {
        return materialService.getMaterialById(materialId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertMaterial(OAuth2Authentication auth,
                               @RequestBody MultiValueMap<String, String> materialMap) throws ValidationException {
        Material material = MapBeanConverter.convertMapToDtoClass(Material.class, materialMap);
        assert material != null;
        material.insertAuthor(auth);
        materialService.insertMaterial(material);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateMaterialById(OAuth2Authentication auth, @PathVariable("materialId") long materialId,
                                   @RequestBody MultiValueMap<String, String> materialMap) throws ValidationException {
        Material material = MapBeanConverter.convertMapToDtoClass(Material.class, materialMap);
        assert material != null;
        material.insertAuthor(auth);
        materialService.updateMaterial(materialId, material);
    }

    private void filterMaterialBySubType(List<Material> materials, String subType) {
        materials.removeIf(
                material -> !material.getSubType().equalsIgnoreCase(subType)
        );
    }
}