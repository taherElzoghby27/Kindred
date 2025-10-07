export class GeneralResponse<T> {
  data: T[];
  page: number;
  size: number;

  constructor(data: T[], page: number, size: number) {
    this.data = data;
    this.page = page;
    this.size = size;
  }
}
