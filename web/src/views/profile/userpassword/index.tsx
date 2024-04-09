import {useState} from 'react'
import styles from './index.module.scss'
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import { PasswordInfo } from '@/interface';
import { message } from 'antd';
import { updatepw } from '@/api/user';

const Userinfo = () => {
  const [pw, setPw] = useState({} as PasswordInfo)
  const [repeat, setRepeat] = useState('')
  const handleClick = () => {
    if (repeat !== pw.newPassword) {
      message.error("重复密码不一致")
      return
    }
    if (pw.oldPassword === pw.newPassword) {
      message.error("新旧密码不能相同")
      return
    }
    const getData = async () => {
      try {
        await updatepw(pw)
        message.success("修改成功")
      } catch (err) {
        message.error('修改失败：'+err)
      }
    }
    getData()
  }
  return (
    <div className={styles.root}>
      <TextField  label="原密码" variant="outlined" size='small' type='password' value={pw.oldPassword} onChange={(e:any) => setPw({...pw, oldPassword: e.target.value})}/>
      <TextField  label="新密码" variant="outlined" size='small' type='password' value={pw.newPassword} onChange={(e:any) => setPw({...pw, newPassword: e.target.value})}/>
      <TextField  label="确认新密码" variant="outlined" size='small' type='password' onChange={(e:any) => setRepeat(e.target.value)}/>
      <Button variant="contained" onClick={handleClick}>修改密码</Button>
    </div>
  )
}

export default Userinfo