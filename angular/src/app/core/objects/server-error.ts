import { ErrorCode } from '../enum/error-code.enum';

export class ServerError {
  error: ErrorCode;
  error_description: string;
}
