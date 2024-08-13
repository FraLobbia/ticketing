export interface RegisterModel {
  name: string;
  surname: string;
  email: string;
  password: string;
}

export interface LoginModel {
  email: string;
  password: string;
}

export interface LoginResponseModel {
  accessToken: string;
}
