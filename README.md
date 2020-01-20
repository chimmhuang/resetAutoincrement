# MySql 主键自增重置器

<p align="left">
	<img src='https://img.shields.io/github/license/chimmhuang/resetAutoincrement' alt='lisence'></img>
	<img src="https://img.shields.io/badge/JDK-1.8%2B-red" alt='jdk'></img>
	<img src="https://img.shields.io/badge/mysql-5.7-blue" alt='mysql'></img>
</p>

#### 介绍
`resetAutoincrement` 是一款基于 `Java` 开发的程序，其功能为重置 `mysql` 主键自增的值为最近的一个。

#### 环境说明
- `JDK`:`1.8`
- `MySql`:`5.7`

#### Todo List
* [ ] 考虑到数据库配置的扩展性，后期会将程序改为从配置文件中获取数据库连接的配置信息
* [X] 录制程序演示视频

#### 程序演示
| 视频网站 | 地址 |
| :-- | :-- |
| 哔哩哔哩 | [https://www.bilibili.com/video/av84268614](https://www.bilibili.com/video/av84268614) |

#### 目录结构

```
.  
├── actuator      该目录下存放执行器接口及其实现类，整个程序的核心实现  
├── config        数据库链接的配置信息存放于此，也可以使用properties配置文件替换  
├── exception     异常类存放于此  
├── pojo          实体类存放于此   
├── run           整个程序的启动目录，执行`main`即可启动程序   
└── validation    该目录下存放校验类的信息，目前只写了一个校验数据库连接配置信息的类  
```

#### 使用说明
1. 本人测试的数据库demo sql语句
```sql
CREATE TABLE `demo` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='demo数据库';

CREATE TABLE `demo02` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `remarks` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='demo02数据库';
```
2. 在`config`目录下配置好数据库的连接信息
3. 在`run`目录下启动`main`方法即可执行

#### 联系作者
- `email`:`chimmhuang@163.com`
- `QQ`:`905369866`