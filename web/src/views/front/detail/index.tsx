import {useLayoutEffect, useState} from 'react'
import { useParams } from 'react-router-dom'
import styles from './index.module.scss'
import { Button } from '@mui/material'
import { getById } from '@/api/order'
import { Good } from '@/interface'
import { getImg } from '@/util'
import { add } from '@/api/cart'
import { message } from 'antd';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import { useViewport } from '@/content/viewportContent'

const GoodDetail = () => {
  const {width} = useViewport();
  const {id} = useParams()
  const [data, setData] = useState({} as Good)
  const [open, setOpen] = useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  useLayoutEffect(() => {
    if (id == undefined || id == null) {
      alert("id错误")
      return
    }
    const getData = async () => {
      try {
        const d = await getById(parseInt(id))
        setData(d)
      } catch (err) {
        alert(err)
      }
    }
    getData()
  }, [])

  const addCart = () => {
    const getData = async () => {
      try {
        await add(data.orderId)
        message.success('添加成功')
      } catch (err) {
        alert(err)
      }
    }
    getData()
  }

  return (
    <>
      {
        width>500?
          <div className={styles.root}>
              <img src={getImg(parseInt(id as string))}/>
              <div className='content'>
                  <span className='title'>{data.title}</span>
                  <div className='comment'>{data.content}</div>
                  <span className='price'>￥{data.price}</span>
                  <span className='time'>{data.createTime} {data.updateTime}</span>
                  {
                    data.type === 'needs' ?
                      <Button className='bu' variant='outlined' onClick={handleClickOpen}>联系买家</Button>
                    :
                      <Button className='bu' variant='outlined' onClick={addCart}>加入购物车</Button>
                  }
              </div>
              <div>
                <Dialog
                  open={open}
                  onClose={handleClose}
                >
                  <DialogTitle id="alert-dialog-title">
                    买家信息
                  </DialogTitle>
                  <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                      <div>买家姓名：张三</div>
                      <div>买家地址：法外之地</div>
                      <div>买家手机号码：114514</div>
                      <div>更新时间：2023-09-11 18:21:11</div>
                    </DialogContentText>
                  </DialogContent>
                  <DialogActions>
                    <Button onClick={handleClose}>确定</Button>
                  </DialogActions>
                </Dialog>
              </div>
          </div>
        :
          <div className={styles.mobile}>
              <img src={getImg(parseInt(id as string))}/>
              <div className='content'>
                  <span className='title'>{data.title}</span>
                  <div className='comment'>{data.content}</div>
                  <span className='price'>￥{data.price}</span>
                  <span className='time'>{data.createTime} {data.updateTime}</span>
                  {
                    data.type === 'needs' ?
                      <Button className='bu' variant='outlined' onClick={handleClickOpen}>联系买家</Button>
                    :
                      <Button className='bu' variant='outlined' onClick={addCart}>加入购物车</Button>
                  }
              </div>
              <div>
                <Dialog
                  open={open}
                  onClose={handleClose}
                >
                  <DialogTitle id="alert-dialog-title">
                    买家信息
                  </DialogTitle>
                  <DialogContent>
                    <DialogContentText id="alert-dialog-description">
                      <div>买家姓名：张三</div>
                      <div>买家地址：法外之地</div>
                      <div>买家手机号码：114514</div>
                      <div>更新时间：2023-09-11 18:21:11</div>
                    </DialogContentText>
                  </DialogContent>
                  <DialogActions>
                    <Button onClick={handleClose}>确定</Button>
                  </DialogActions>
                </Dialog>
              </div>
          </div>
      }
    </>
  )
}

export default GoodDetail