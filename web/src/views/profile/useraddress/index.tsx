import {useState, useEffect} from 'react'
import styles from './index.module.scss'
import { Address } from '@/interface'
import { addAddr, getAddr } from '@/api/address'
import Button from '@mui/material/Button';
import { message } from 'antd'
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';

const Useraddress = () => {
  const [addr, setAddr] = useState([] as Address[])
  const [open, setOpen] = useState(false);
  const [toAdd, setToAdd] = useState({isDefault:false} as Address)

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    const getData = async () => {
      try {
        await addAddr(toAdd)
        message.success('添加成功')
        const d = await getAddr()
        setAddr(d)
      } catch (err) {
        message.error('添加失败：'+err)
      }
    }
    getData()
  };
  useEffect(() => {
    const getData = async () => {
      try {
        const d = await getAddr()
          setAddr(d)
      } catch (err) {
          message.error('获取数据失败：'+err)
      }
    }
    getData()
  }, [])
  return (
    <div className={styles.root}>
      <div className='title'>我的收货地址</div>
      <hr className='line'/>
      <Button variant='contained' onClick={handleClickOpen}>添加</Button>
      {
        addr.map((x: Address, index: number) => 
          (
            <div className='addr' key={index}>
              <span>收货人：{x.consignee}</span>
              <span>收货地址：{x.addressDetail}</span>
              <span>收货手机号：{x.phone}</span>
            </div>
          )
        )
      }
        <Dialog open={open} onClose={handleClose}>
          <DialogTitle>添加地址</DialogTitle>
          <DialogContent>
            <TextField
              autoFocus
              margin="dense"
              id="name"
              label="收货人"
              type='text'
              fullWidth
              variant="standard"
              value={toAdd.consignee}
              onChange={(e:any)=>setToAdd({...toAdd, consignee: e.target.value})}
            />
            <TextField
              autoFocus
              margin="dense"
              id="addr"
              label="收货地址"
              type='text'
              fullWidth
              variant="standard"
              value={toAdd.addressDetail}
              onChange={(e:any)=>setToAdd({...toAdd, addressDetail: e.target.value})}
            />
            <TextField
              autoFocus
              margin="dense"
              id="phone"
              label="收货手机号"
              type='text'
              fullWidth
              variant="standard"
              value={toAdd.phone}
              onChange={(e:any)=>setToAdd({...toAdd, phone: e.target.value})}
            />
          </DialogContent>
          <DialogActions>
            <Button onClick={() => setOpen(false)}>取消</Button>
            <Button onClick={handleClose}>确定</Button>
          </DialogActions>
        </Dialog>
    </div>
  )
}

export default Useraddress