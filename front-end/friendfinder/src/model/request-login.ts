export class RequestLogin {
  id: number;
  email: string;
  password: string;

  constructor(email: string, password: string) {
    this.password = password;
    this.email = email;
  }
}
