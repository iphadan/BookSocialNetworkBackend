/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { Book } from '../models/book';
import { User } from '../models/user';
export interface BookTransactionHistory {
  book?: Book;
  createdBy?: number;
  createdDate?: string;
  id?: number;
  lastModifiedBy?: number;
  lastModifiedDate?: string;
  returned?: boolean;
  returnedApproved?: boolean;
  user?: User;
}
