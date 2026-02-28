import { useEffect, useState } from "react";

import { postsController } from "@/services/api/controllers/post-controller";

import { PostDto } from "@/types/post/post.type";

interface ShowPostProps {
    postId: number | undefined
}

const ShowPost = (props: ShowPostProps) => {
    const [post, setPost] = useState<PostDto>();

    useEffect(() => { 
        if (props.postId) {
            postsController.getPostId(props.postId)
                .then((response) => {
                    setPost(response.data);
                })
                .catch((error) => console.log(error));
        }
    }, [props.postId]);

    const changeContentPost = () => {
        if (props.postId && post) {
            postsController.editPost(props.postId, post)
                .then((response) => {
                    setPost(response.data);
                })
                .catch((err) => console.log(err));
        }
    }

    return props.postId ?
        <div>
            <input type="text" value={post?.content} onChange={(e) => setPost({ ...post, content: e.target.value })} />
            <button onClick={changeContentPost}>Изменить</button>
        </div>
        :
        null
}

export default ShowPost;