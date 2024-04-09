import {useState, useEffect} from 'react'
import { Button } from '@mui/material'
import styles from './index.module.scss'
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import TextField from '@mui/material/TextField';
import Checkbox from '@mui/material/Checkbox';
import IconButton from '@mui/material/IconButton';
import DeleteForeverIcon from '@mui/icons-material/DeleteForever';
import { getImg } from '@/util';
import { Address, Cart } from '@/interface';
import { deleteCart, orderCommit, show, updataCount } from '@/api/cart';
import Avatar from '@mui/material/Avatar';
import HomeIcon from '@mui/icons-material/Home';
import ListItemAvatar from '@mui/material/ListItemAvatar';
import ListItemText from '@mui/material/ListItemText';
import DialogTitle from '@mui/material/DialogTitle';
import Dialog from '@mui/material/Dialog';
import { blue } from '@mui/material/colors';
import { message } from 'antd';
import { getAddr } from '@/api/address';

const Shopcart = () => {

    const [list, setList] = useState([] as Cart[]) 
    const [checkList, setCheckList] = useState([] as number[])
    const [addr, setAddr] = useState([] as Address[])
    const [curaddr, setCuraddr] = useState(0)
    const [open, setOpen] = useState(false)

    useEffect(() => {
        const getData = async () => {
        try {
            const d = await show()
            setList(d)
            const a = await getAddr()
            setAddr(a)
        } catch (err) {
            message.error('获取购物车列表失败：' + err)
        }
        }
        getData()
    },[])

    const handleDelete = (id: number, index: number) => {
        setCheckList(checkList.filter(function (number) {
            return number !== index;
        }))
        const getData = async () => {
        try {
            await deleteCart(id)
            const d = await show()
            setList(d)
            message.success('删除成功')
        } catch (err) {
            message.error('删除失败：' + err)
        }
        }
        getData()
    }

    const handleCheck = (e: any, index: number) => {
        if (e.target.checked) {
            if (!checkList.includes(index)) {
                setCheckList([index, ...checkList])
            }
        } else {
                setCheckList(checkList.filter(function (number) {
                return number !== index;
            }))
        }
    } 

    const updateCount = (sid: number, index: number, c: number) => {
        if (c >= 0) {
            setCheckList(checkList.filter(function (number) {
                return number !== index;
            }))
            const getData = async () => {
            try {
                await updataCount(sid, c)
                const d = await show()
                setList(d)
            } catch (err) {
                message.error('更新商品数量失败：' + err)
            }
            }
            getData()
        }
    }
    
    const commit = () => {
         const c = list.reduce(function (accumulator, c) {
                    return accumulator + c.count*parseFloat(c.price);
                }, 0)
        const aid = addr[curaddr].id
        const getData = async () => {
        try {
            if (aid === undefined) {
                message.error('地址错误')
                return
            }
            await orderCommit(aid, c, list)
            const d = await show()
            setList(d)
            message.success("提交成功")
        } catch (err) {
            message.error('更新商品数量失败：' + err)
        }
        }
        getData()
    }

  return (
    <div className={styles.root}>
        <div className='location'>
            <div className='info'>
                <label>收货人：{addr.length>0?addr[curaddr].consignee:''}</label>
                <label>收货地址：{addr.length>0?addr[curaddr].addressDetail:''}</label>
                <label>收货手机号：{addr.length>0?addr[curaddr].phone:''}</label>
            </div>
            <Button className='bu' variant='outlined' onClick={() => setOpen(true)}>更改地址</Button>
        </div>
        <div className='title'>购物车</div>
        <div className='head'>
            <span className='h1'>商品</span>
            <span className='h2'>单价</span>
            <span className='h3'>数量</span>
            <span className='h4'>操作</span>
        </div>
        <div className='item'>
            <List sx={{ 
                width: '100%', 
                bgcolor: 'background.paper',         
                overflow: 'auto',
                maxHeight: '50vh', }}>
            {list.map((v: Cart, index: number) => {
                return (
                <ListItem
                    key={index}
                    secondaryAction={
                    <IconButton edge="end" aria-label="comments" onClick={() => handleDelete(v.shoppingId, index)}>
                        <DeleteForeverIcon/>
                    </IconButton>
                    }
                    disablePadding
                >
                    <ListItemButton role={undefined}  dense>
                    <ListItemIcon>
                        <Checkbox
                        edge="start"
                        tabIndex={-1}
                        disableRipple
                        checked={checkList.includes(index)?true:false}
                        onChange={(e: any) => handleCheck(e, index)}
                        />
                    </ListItemIcon>
                    <img src={getImg(index)}/>
                    <div className='item-info'>
                        <div className='text1'>{v.title}</div>
                        <p className='text2'>{v.content}</p>
                    </div>
                    <span className='price'>￥{v.price}</span>
                    </ListItemButton>
                    <TextField
                        className='number'
                        id="outlined-number"
                        label="数量"
                        type="number"
                        size='small'
                        value={v.count}
                        sx={{
                            width: '100px'
                        }}
                        onChange={(e) => updateCount(v.shoppingId, index, parseInt(e.target.value))}
                        InputLabelProps={{
                            shrink: true,
                        }}
                    />
                </ListItem>
                );
            })}
            </List>          
        </div>
        <div className='footer'>
            <Button className='footer-bu' variant='contained' onClick={() => setCheckList([])}>取消选择</Button>
            <span className='price'>总价：￥{
                list.reduce(function (accumulator, c) {
                    return accumulator + c.count*parseFloat(c.price);
                }, 0)
            }</span>
            <Button className='footer-bu' variant='contained' onClick={commit} >提交订单</Button>
        </div>
        <Dialog onClose={() => setOpen(false)} open={open}>
        <DialogTitle>选择地址</DialogTitle>
        <List sx={{ pt: 0 }}>
            {addr.map((a, index) => (
            <ListItem disableGutters key={index}>
                <ListItemButton onClick={() => {setCuraddr(index);setOpen(false)}}>
                <ListItemAvatar>
                    <Avatar sx={{ bgcolor: blue[100], color: blue[600] }}>
                        <HomeIcon />
                    </Avatar>
                </ListItemAvatar>
                <div style={{
                    width: '500px',
                    display: 'flex',
                    justifyContent: 'space-between'
                    }}>
                    <span>{'收货人：'+a.consignee}</span>
                    <span>{'收货地址：'+a.addressDetail}</span>
                    <span>{'收货手机号：'+a.phone}</span>
                </div>
                </ListItemButton>
            </ListItem>
            ))}
        </List>
        </Dialog>
    </div>
  )
}

export default Shopcart