export class ErrorCode {
  static Unauthorized = new ErrorCode('unauthorized');

  static InvalidGrant = new ErrorCode('invalid_grant');

  static ResourceNotFound = new ErrorCode('resource_not_found');

  /* --> Get reset password code error */
  static AccountNotFound = new ErrorCode('account_not_found_code');

  static EmailIncorrect = new ErrorCode('email_code');
  /* <-- Get reset password code error */

  /* --> Reset password error */
  static ClientIDError = new ErrorCode('client_error_code');

  static CodeNotFound = new ErrorCode('code_not_found_code');
  /* <-- Reset password error */

  static RequestCompleted = new ErrorCode('request_completed');

  static DatabaseException = new ErrorCode('database_exception');

  constructor(public value: string) { }

  toString() { return this.value; }
}
