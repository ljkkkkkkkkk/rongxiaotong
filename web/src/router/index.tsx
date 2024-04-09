import {useRoutes} from 'react-router-dom'
import Home from '@/views/home'
import Guide from '@/views/guide'
import Front from '@/views/front'
import Knowledge from '@/views/knowledge'
import Purchase from '@/views/purchase'
import KnowDetail from '@/views/knowledge/detail'
import GoodDetail from '@/views/front/detail'
import GuideDetail from '@/views/guide/detail'
import PurchaseDetail from '@/views/purchase/detail'
import Login from '@/views/login'
import Register from '@/views/register'
import Shopcart from '@/views/shopcart'
import Profile from '@/views/profile'
import Userinfo from '@/views/profile/userinfo'
import Useraddress from '@/views/profile/useraddress'
import Userpassword from '@/views/profile/userpassword'

const routes = [
    {
        path: '/',
        element: <Home/>,
        children: [
            {
                path: '',
                element: <Front/>
            },
            {
                path: 'purchase',
                element: <Purchase/>
            },
            {
                path: 'guide',
                element: <Guide/>
            },
            {
                path: 'knowledge',
                element: <Knowledge/>
            },
            {
                path: 'shopcart',
                element: <Shopcart/>
            },
            {
                path:'knowDetail/:index',
                element: <KnowDetail/>
            },
            {
                path: 'goodDetail/:id',
                element: <GoodDetail/>
            },
            {
                path: 'guideDetail/:id',
                element: <GuideDetail/>
            },
            {
                path: 'purchaseDetail/:id',
                element: <PurchaseDetail/>
            },
            {
                path: 'profile',
                element: <Profile/>,
                children: [
                    {
                        path: 'userinfo',
                        element: <Userinfo/>
                    },
                    {
                        path: 'useraddress',
                        element: <Useraddress/>
                    },
                    {
                        path: 'userpassword',
                        element: <Userpassword/>
                    }
                ]
            }

        ]

    },
    {
        path: '/login',
        element: <Login/>
    },
    {
        path: '/register',
        element: <Register/>
    }
]

const Router = () => useRoutes(routes)

export default Router;