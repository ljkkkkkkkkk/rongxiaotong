const imgSet = [
    'https://img0.baidu.com/it/u=2941347737,1638271179&fm=253&fmt=auto&app=138&f=JPEG?w=753&h=500',
    'https://img2.baidu.com/it/u=3919000538,4104808434&fm=253&fmt=auto&app=138&f=JPEG?w=749&h=500',
    'https://img2.baidu.com/it/u=1686277436,2183863935&fm=253&fmt=auto&app=138&f=JPEG?w=750&h=500',
    'https://img1.baidu.com/it/u=2883367724,3277733711&fm=253&fmt=auto&app=138&f=JPEG?w=750&h=500',
    'https://img2.baidu.com/it/u=1723487613,1320594905&fm=253&fmt=auto&app=138&f=JPEG?w=755&h=500',
    'https://img0.baidu.com/it/u=3128140630,295602708&fm=253&fmt=auto&app=120&f=PNG?w=570&h=408',
    'https://img1.baidu.com/it/u=552128505,3659699595&fm=253&fmt=auto&app=138&f=JPEG?w=667&h=500',
    'https://img2.baidu.com/it/u=1988581837,849907455&fm=253&fmt=auto&app=138&f=JPEG?w=680&h=500',
    'https://img2.baidu.com/it/u=1656978894,695550800&fm=253&fmt=auto&app=138&f=JPEG?w=918&h=500',
    'https://img2.baidu.com/it/u=2437074844,1115031785&fm=253&fmt=auto&app=138&f=JPEG?w=1099&h=500',
    'https://img2.baidu.com/it/u=2047572473,689292954&fm=253&fmt=auto&app=138&f=JPEG?w=750&h=500',
    'https://img0.baidu.com/it/u=4218172395,1306662107&fm=253&fmt=auto&app=138&f=JPEG?w=499&h=281',
    'https://img0.baidu.com/it/u=1260937284,1189538432&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=334',
    'https://img0.baidu.com/it/u=3624654156,987920885&fm=253&fmt=auto&app=120&f=JPEG?w=1000&h=566',
    'https://img1.baidu.com/it/u=2087492053,1438324506&fm=253&fmt=auto&app=138&f=JPEG?w=766&h=500',
    'https://img2.baidu.com/it/u=3860360177,3631272460&fm=253&fmt=auto&app=138&f=JPEG?w=640&h=362',
    'https://img2.baidu.com/it/u=274897526,3558372673&fm=253&fmt=auto&app=138&f=JPG?w=585&h=358',
    'https://img2.baidu.com/it/u=3001934528,3253592966&fm=253&fmt=auto&app=138&f=PNG?w=648&h=428',
    'https://img1.baidu.com/it/u=1390144289,852431941&fm=253&fmt=auto&app=138&f=JPEG?w=1000&h=500',
    'https://img1.baidu.com/it/u=630434706,3403207685&fm=253&fmt=auto&app=120&f=JPEG?w=654&h=367',
]

const expertImg = [
    'https://img0.baidu.com/it/u=4217896712,291708706&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=544',
    'https://img0.baidu.com/it/u=2599928561,1257628442&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=333',
    'https://img0.baidu.com/it/u=3524304294,3504764524&fm=253&fmt=auto&app=120&f=JPEG?w=608&h=342',
    'https://img2.baidu.com/it/u=2694729424,3203689064&fm=253&fmt=auto&app=138&f=JPEG?w=461&h=499',
    'https://k.sinaimg.cn/n/tech/crawl/404/w550h654/20220210/c7c9-cdbe45f9be98375cc90c8dccb0c77b52.jpg/w700d1q75cms.jpg?by=cms_fixed_width',
    'https://picx.zhimg.com/v2-e66e0ab190802fed675fb3a236cefc25_720w.jpg?source=172ae18b',
    'https://img2.baidu.com/it/u=750825790,2446951666&fm=253&fmt=auto&app=138&f=JPEG?w=819&h=500',
]

export function getImg(index: number) {
  const i = index % imgSet.length;
  return imgSet[i]
}

export function getExpert(index: number) {
  const i = index % expertImg.length;
  return expertImg[i];
}
