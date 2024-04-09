import React from 'react'
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import FormControlLabel from '@mui/material/FormControlLabel';
import Button from '@mui/material/Button';
import Checkbox from '@mui/material/Checkbox';
import TextField from '@mui/material/TextField';
import Avatar from '@mui/material/Avatar';
import Typography from '@mui/material/Typography';
import styles from './index.module.scss'
import { LoginInfo } from '@/interface';
import { useDispatch } from 'react-redux';
import { login } from '@/api/login';
import { setToken } from '@/store/slices/loginSlice';
import { useNavigate } from 'react-router-dom';
import { message } from 'antd';

const Login = () => {
  const dispatch = useDispatch();
  const navigateTo = useNavigate();
  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const loginInfo: LoginInfo = {
      username: data.get('username') as string,
      password: data.get('password') as string,
    }
    const getData = async () => {
      try {
        const d = await login(loginInfo)
        if (d) {
          dispatch(setToken(d))
          message.success('登录成功')
          navigateTo('/')
        } else {
          alert("密码或用户名错误")
          return
        }
      } catch (err) {
        alert(err)
      }
    }
    getData()
  };
  return (
    <div className={styles.root}>
      <div className='login'>
        <Box
            sx={{
              display: 'flex',
              flexDirection: 'column',
              alignItems: 'center',
            }}
          >
            <Avatar sx={{ m: 1, bgcolor: 'secondary.main' }}>
              <LockOutlinedIcon/>
            </Avatar>
            <Typography component="h1" variant="h5">
              登录
            </Typography>
            <Box component="form" onSubmit={handleSubmit} noValidate sx={{ mt: 1 }}>
              <TextField
                margin="normal"
                required
                fullWidth
                id="username"
                label="用户名"
                name="username"
                color='success'
                autoFocus
              />
              <TextField
                margin="normal"
                required
                fullWidth
                name="password"
                label="密码"
                type="password"
                id="password"
                color='success'
                autoComplete="current-password"
              />
              <FormControlLabel
                control={<Checkbox value="remember" color="primary" />}
                label="记住我"
              />
              <Button
                type="submit"
                fullWidth
                color='success'
                variant="contained"
                sx={{ mt: 3, mb: 2 }}
              >
                登录
              </Button>
            </Box>
          </Box>
      </div>

    </div>
  )
}

export default Login