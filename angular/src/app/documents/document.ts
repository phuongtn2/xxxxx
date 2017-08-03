import { BaseModel } from '../core/models/base-model';

export class Document extends BaseModel {
  id: number;
  clientPrimaryKey: number;
  documentName: string;
  file: any;
  companyId: string;
  repositoryId: string;
  directoryId: number;
  description: string;
  extension: string;
  status: string;
  type: string;
  size: string;
  version: string;
  documentOwner: string;
  uploadedDate: number;
}
