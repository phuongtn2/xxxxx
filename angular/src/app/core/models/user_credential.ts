import { User } from './user';

export class UserCredential extends User {
  access_token: string;
  token_type: string;
  refresh_token: string;
  expires_in: number;
  scope: string;
}
