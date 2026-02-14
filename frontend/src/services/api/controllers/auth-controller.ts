import { api } from "..";
import { loginDto, resLoginDto } from "@/types/auth/login.type";

export const authController = {
    // login
    login: (data: loginDto) => {
        return api.post<resLoginDto>('/api/v1/auth/login', data);
    }
}