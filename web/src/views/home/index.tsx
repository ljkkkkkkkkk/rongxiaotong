import styles from './index.module.scss'
import { Tabs, Tab, ButtonGroup, Button } from '@mui/material'
import Logo from '@/assets/img/logo.png'
import {Outlet, useNavigate} from 'react-router-dom'
import { useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { clearToken } from '@/store/slices/loginSlice'

const Home = () => {
    const navigateTo = useNavigate();
    const [value, setValue] = useState(0)
    const dispatch = useDispatch();

    const token = useSelector((state: any) => state.login.token)

  return (
    <div className={styles.root}>
        <div className='top'>
            <div className='top-logo'>
                <img src={Logo}/>
                <div className='top-text'>
                    <span className='text1'>融销通</span>
                    <span className='text2'>超导零电组</span>
                </div>
            </div>
            <div className='login'>
                {
                    token == '' ? 
                        <ButtonGroup variant="text" aria-label="text button group" color='inherit'>
                            <Button onClick={() => navigateTo('/login')}>登录</Button>
                            <Button onClick={() => navigateTo('/register')}>注册</Button>
                        </ButtonGroup>
                    :
                        <Button color='inherit' onClick={() => dispatch(clearToken()) }>退出登录</Button>
                }
            </div>
        </div>
        <div className='header'>
            <Tabs
                TabIndicatorProps={{style: {background:'#a0c69d'}}}
                value={value}
                variant="scrollable"
                scrollButtons="auto"
                aria-label="scrollable auto tabs example"
                textColor="inherit"
                >
                <Tab label="首页" onClick={() => {setValue(0);navigateTo('')}}/>
                <Tab label="求购需求" onClick={() => {setValue(1);navigateTo('purchase')}}/>
                <Tab label="农业知识" onClick={() => {setValue(2);navigateTo('knowledge')}}/>
                <Tab label="专家指导" onClick={() => {setValue(3);navigateTo('guide')}}/>
                <Tab label="购物车" onClick={() => {setValue(4);navigateTo('shopcart')}}/>
                <Tab label="个人中心"onClick={() => {setValue(5);navigateTo('profile')}} />
            </Tabs>
        </div>
        <div className='content'>
            <Outlet/>
        </div>
    </div>
  )
}

export default Home