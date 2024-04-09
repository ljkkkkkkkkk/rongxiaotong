import React from 'react'
import Box from '@mui/material/Box';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Avatar from '@mui/material/Avatar';
import Typography from '@mui/material/Typography';
import styles from './index.module.scss'
import { RegisterInfo } from '@/interface';
import { register } from '@/api/register';
import { useNavigate } from 'react-router-dom';

const Register = () => {
  const navigateTo = useNavigate();
  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    const data = new FormData(event.currentTarget);
    const registerInfo: RegisterInfo = {
      userName: data.get('username') as string,
      nickName: data.get('nickName') as string,
      password: data.get('password') as string,
      avatar: "null"
    }
    const getData = async () => {
      try {
        await register(registerInfo)
        navigateTo('/login')
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
              注册
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
                id="nickName"
                label="昵称"
                name="nickName"
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
              <Button
                type="submit"
                fullWidth
                color='success'
                variant="contained"
                sx={{ mt: 3, mb: 2 }}
              >
                注册
              </Button>
            </Box>
          </Box>
      </div>

    </div>
  )
}

export default Register