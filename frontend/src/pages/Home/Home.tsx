import { useEffect, useState } from 'react';

import './Home.scss';

import { postsController } from '@/services/api/controllers/post-controller';

import { codeResponseError } from '@/utils/api-response/code.responese';

import { PostDto } from '@/types/post/post.type';
import { PageableObject } from '@/types/page/page.types';
import { ApiError } from '@/types/error-api/error-api.type';
import { ApiResponse, getDefaultPageable, Pageable } from '@/types/common/pageable.type';

const Home = () => {
    const [error, setError] = useState('');
    const [posts, setPosts] = useState<PostDto[]>();
    const [contentPost, setContentPost] = useState('');
    const [pageable] = useState<Pageable>(getDefaultPageable());

    const fetchPosts = async () => {
        setError('');

        try {
            // Явно указываем тип ответа
            const response = await postsController.getPosts(pageable) as ApiResponse<PageableObject>;

            // response.data теперь типизирован как PageableObject
            setPosts(response.data.content as PostDto[]);
            console.log('Посты получены:', response);
        } catch (err) {
            const error = err as ApiError;
            console.log('Ошибка:', error);

            if (error.response?.status) {
                setError(codeResponseError(error.response.status));
            } else {
                setError('Произошла неизвестная ошибка');
            }
        }
    };

    useEffect(() => {
        fetchPosts();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const createNewPost = async () => {
        const data = {
            content: contentPost
        };

        try {
            const response = await postsController.createPost(data) as ApiResponse<PageableObject>;

            if (response.status === 200) {
                await fetchPosts();
                setContentPost('');
            }
        } catch (err) {
            const error = err as ApiError;
            console.log('Ошибка:', error);

            if (error.response?.status) {
                setError(codeResponseError(error.response.status));
            } else {
                setError('Произошла неизвестная ошибка');
            }
        }
    };

    return (
        <div className="home-page">
            {error && <div style={{ color: 'red' }}>{error}</div>}

            <ul>
                {posts && posts.length !== 0 ? (
                    posts.map((item, key) => (
                        <li key={key}>{item.content}</li>
                    ))
                ) : (
                        <li>Нет постов</li>
                )}
            </ul>

            <input
                value={contentPost}
                onChange={(e) => setContentPost(e.target.value)}
                placeholder="Введите текст поста"
            />
            <button onClick={createNewPost}>
                Создать
            </button>
        </div>
    );
};

export default Home;