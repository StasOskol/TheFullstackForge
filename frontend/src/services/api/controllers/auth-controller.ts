import { api } from "..";
import { authLoginDto, resAuthLoginDto } from "@/types/auth/auth.type";

export const authController = {
    login: (data: authLoginDto) => {
        return api.post<resAuthLoginDto>('/api/v1/auth/login', data);
    }
}