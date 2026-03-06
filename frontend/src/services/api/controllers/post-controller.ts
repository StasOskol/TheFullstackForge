
import { api } from "..";
import { resAuthLoginDto } from "@/types/auth/auth.type";
import { Pageable } from "@/types/common/pageable.type";

export const postController = {
    getPost: (pageable: Pageable) => {
        return api.post<resAuthLoginDto>(`/posts/?page=${pageable.page}&size=${pageable.size}&sort=${pageable.sort?.join(",")}`);
    }
}