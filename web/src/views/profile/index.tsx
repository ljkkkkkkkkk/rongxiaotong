import styles from './index.module.scss'
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import {useEffect} from 'react'
import AccountCircleIcon from '@mui/icons-material/AccountCircle';
import AddHomeIcon from '@mui/icons-material/AddHome';
import CachedIcon from '@mui/icons-material/Cached';
import { Outlet, useNavigate } from 'react-router-dom';

const Profile = () => {
    const navigateTo = useNavigate();
    useEffect(() => {
        navigateTo('userinfo')
    }, [])
  return (
    <div className={styles.root}>
        <div className='nav'>
            <Tabs
                orientation="vertical"
                variant="scrollable"
                aria-label="Vertical tabs example"
                sx={{ borderRight: 1, borderColor: 'divider' }}
            >
                <Tab icon={<AccountCircleIcon/>} iconPosition='start' label="基本信息" onClick={() => navigateTo('userinfo')}/>
                <Tab icon={<AddHomeIcon/>} iconPosition='start' label="收货地址" onClick={() => navigateTo('useraddress')} />
                <Tab icon={<CachedIcon/>} iconPosition='start' label="修改密码" onClick={() => navigateTo('userpassword')}/>
            </Tabs>
        </div>
        <div className='body'>
            <Outlet/>
        </div>
    </div>
  )
}

export default Profile