import {AccountDetailsVm} from './account-details-vm';

export class AccountVm {
  id: number;
  firstName: string;
  lastName: string;
  accountDetails?: AccountDetailsVm;
}
