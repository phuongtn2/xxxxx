export class BaseModel {
  static assign(data) {
    if (!data) {
      return null;
    }
    return Object.assign(new this(), data);
  }
}
