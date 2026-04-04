import { useEffect, useState } from 'react';
import './Home.scss';
import { postController } from '@/services/api/controllers/post-controller';
import { getDefaultPageable, Pageable } from '@/types/common/pageable.type';

const Home = () => {
    // const [data, setData] = useState<[]>();

    const [pageable] = useState<Pageable>(getDefaultPageable());

    useEffect(() => {
        postController.getPost(pageable)
            .then((response) => {
                console.log(response);
                //setData(response.data);
            })
            .catch((error) => console.log(error));
    }, [pageable])

    return <div className="home-page">
        <div className="container">
            <h1>Главная страница</h1>
            <p>Здесь будет контент главной страницы</p>
        </div>
    </div>
};

export default Home;