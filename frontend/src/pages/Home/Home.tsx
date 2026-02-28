import { useEffect, useState } from 'react';

import './Home.scss';

import { postsController } from '@/services/api/controllers/post-controller';

import { getDefaultPageable, Pageable } from '@/types/common/pageable.type';
import { PostDto } from '@/types/post/post.type';

import ShowPost from '@/components/home/ShowPost';

const Home = () => {
    const [posts, setPosts] = useState<PostDto[]>([]);
    const [contentPost, setContentPost] = useState('');
    const [error, setError] = useState('');

    const [postActiveId, setPostActiveId] = useState<number | undefined>(undefined);

    const [pageable] = useState<Pageable>(getDefaultPageable());

    const fetchPosts = () => {
        postsController.getPosts(pageable)
            .then(response => {
                setPosts(response.data.content as PostDto[]);
            })
            .catch(() => setError('Ошибка загрузки'));
    };

    useEffect(() => {
        fetchPosts();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    const createNewPost = () => {
        if (!contentPost.trim()) return;

        postsController.createPost({ content: contentPost })
            .then(() => {
                fetchPosts();
                setContentPost('');
                setError('');
            })
            .catch(() => setError('Ошибка создания'));
    };

    return (
        <div className="home-page">
            {error && <div style={{ color: 'red' }}>{error}</div>}

            <ul>
                {
                    posts && posts.length !== 0 ?
                        posts.map((item, key) => (
                            <li key={key}>
                                {item.content}
                                <button
                                    className='btn-post-edit'
                                    onClick={() => setPostActiveId(key + 1)}
                                >
                                    Просмотреть / Изменить
                                </button>
                            </li>
                        ))
                        :
                        <li>Нет постов</li>
                }
            </ul>

            <input
                value={contentPost}
                onChange={(e) => setContentPost(e.target.value)}
                placeholder="Новый пост"
            />
            <button onClick={createNewPost}>Создать</button>
            <ShowPost
                postId={postActiveId}
            />
        </div>
    );
};

export default Home;