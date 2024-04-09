import styles from './index.module.scss'
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';

const Userinfo = () => {
  return (
    <div className={styles.root}>
      <img src='123' alt='上传头像'/>
      <TextField  label="昵称" variant="outlined" size='small'/>
      <TextField  label="姓名" variant="outlined" size='small'/>
      <TextField  label="手机号" variant="outlined" size='small'/>
      <TextField  label="身份证" variant="outlined" size='small'/>
      <TextField  label="地址" variant="outlined" size='small'/>
      <Button variant="contained">修改</Button>
    </div>
  )
}

export default Userinfo