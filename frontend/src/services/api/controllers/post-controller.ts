import { api } from "..";
import { PostDto } from "@/types/post/post.type";
import { Pageable } from "@/types/common/pageable.type";
import { PageableObject } from "@/types/page/page.types";

export const postsController = {
    getPosts: (pageable: Pageable) => {
        return api.get<PageableObject>(`/posts/?page=${pageable.page}&size=${pageable.size}&sort=${pageable.sort?.join(",")}`);
    },

    createPost: (data: PostDto) => {
        return api.post<PageableObject>(`/posts/`, data);
    }
}