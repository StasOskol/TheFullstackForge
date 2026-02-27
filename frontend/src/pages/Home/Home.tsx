import { useEffect, useState } from 'react';
import './Home.scss';

import { postsController } from '@/services/api/controllers/post-controller';
import { getDefaultPageable, Pageable } from '@/types/common/pageable.type';
import { PostDto } from '@/types/post/post.type';

const Home = () => {
    const [posts, setPosts] = useState<PostDto[]>([]);
    const [contentPost, setContentPost] = useState('');
    const [error, setError] = useState('');

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
                {posts.map((post, i) => (
                    <li key={i}>{post.content}</li>
                ))}
            </ul>

            <input
                value={contentPost}
                onChange={(e) => setContentPost(e.target.value)}
                placeholder="Новый пост"
            />
            <button onClick={createNewPost}>Создать</button>
        </div>
    );
};

export default Home;