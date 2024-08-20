<p align="center"> <img src="https://github.com/user-attachments/assets/e11118d4-c536-4811-a3c4-98a476edf558" width=300px> </p>

<div id="table">

# ✏️ Table
* ### [🏷️ Introduction to the Project](#a)
* ### [📆 Development Period](#b)
* ### [🖇️ Team](#c)
* ### [⚙️ Tech Stack](#d)
* ### [🧱 Project Architecture](#e)
* ### [📦 Package Structure](#f)
* ### [🔑 Environment Variable](#g)
* ### [📑 ERD DIAGRAM](#h)
* ### [🗂️ API Document](#i)
<br>

<div id="a">

# 🏷️ Introduction to the Project

**"Echo"** 프로젝트는 팀스파르타 Java 5기 최종 프로젝트 **A8조**의 팀 프로젝트로,

### 1. N:M 채팅
### 2. N:M 화상채팅
### 3. 1:1 DM

등의 기능이 구현된 실시간 메신저 커뮤니티 웹 애플리케이션입니다.

[(Back to top)](#table)

<br>

<div id="b">

# 📆 Development Period

* ### 개발 : 2024.07.17 - 2024.08.20 / 5주
* ### 발표 : 2024.08.21(수)

[(Back to top)](#table)

<br>

<div id="c">

# 🖇️ Team
<table>
    <tbody>
        <tr>
            <td align="center"> <a href="https://github.com/hyun1202"> <img src="https://avatars.githubusercontent.com/u/60086998?v=4" width="150px;" alt=""/> </a> <br> <b> 정현경 [리더] </b> </td>
            <td align="center"> <a href="https://github.com/hsd9681"> <img src="https://avatars.githubusercontent.com/u/39897041?v=4" width="150px;" alt=""/> </a> <br> <b> 홍성도 [부리더] </b> </td>
            <td align="center"> <a href="https://github.com/kiseokkm"> <img src="https://avatars.githubusercontent.com/u/132454778?v=4" width="150px;" alt=""/> </a> <br> <b> 김기석 </b> </td>
            <td align="center"> <a href="https://github.com/Berithx"> <img src="https://avatars.githubusercontent.com/u/154594004?v=4" width="150px;" alt=""/> </a> <br> <b> 이유환 </b> </td>
            <td align="center"> <a href="https://github.com/HyeonjinChoi"> <img src="https://avatars.githubusercontent.com/u/63872787?v=4" width="150px;" alt=""/> </a> <br> <b> 최현진 </b> </td>
        </tr>
        <tr>
            <td>
                <font size="2">ㆍProject Chief </font>
                <br>
                <font size="2">ㆍUser Domain</font>
                <br>
                &ensp;&ensp;<font size="2">&ensp;- basic func</font>
                <br>
                <font size="2">ㆍAuth Domain</font>
                <br>
                <font size="2">ㆍThread Domain</font>
                <br>
                <font size="2">ㆍGlobal Issue</font>
                <br>
                <font size="2">ㆍSpring Security</font>
                <br>
                <font size="2">ㆍSSE</font>
                <br>
            </td>
            <td>
                <font size="2">ㆍFront-End Chief</font>
                <br>
                <font size="2">ㆍMedia Chat Domain</font>
                <br>
                <font size="2">&ensp;&ensp;&ensp;- 1:1 Video</font>
                <br>
                <font size="2">ㆍOAuth2 (Kakao)</font>
                <br>
                <font size="2">ㆍDirect Message</font>
                <br>
            </td>
            <td>
                <font size="2">ㆍUser Domain</font>
                <br>
                <font size="2">&ensp;&ensp;&ensp;- additional func</font>
                <br>
                <font size="2">ㆍSpace Domain</font>
                <br>
                <font size="2">ㆍChannel Domain</font>
                <br>
                <font size="2">ㆍFriend Domain</font>
                <br>
                <font size="2">ㆍCI/CD, Deployment</font>
                <br>
                <font size="2">&ensp;&ensp;with AWS and</font>
                <br>
                <font size="2">&ensp;&ensp;Github Action</font>
                <br>
            </td>
            <td>
                <font size="2">ㆍText Chat</font>
                <br>
                <font size="2">&ensp;&ensp;&ensp;- N:M Chat</font>
                <br>
                <font size="2">ㆍS3 Service</font>
                <br>
                <font size="2">ㆍRedis Pub/Sub</font>
                <br>
            </td>
            <td>
                <font size="2">ㆍMedia Chat Domain</font>
                <br>
                <font size="2">&ensp;&ensp;&ensp;- N:M Video</font>
                <br>
                <font size="2">&ensp;&ensp;&ensp;- Screen Sharing</font>
                <br>
                <font size="2">ㆍTyping Indicator</font>
                <br>
                <font size="2">ㆍChat Room User Limit</font>
                <br>
            </td>
        </tr>
    </tbody>
</table>

[(Back to top)](#table)

<br>

<div id="d">

# ⚙️ Tech Stack

|     Type     |                                                                                                                                                                                              Tech                                                                                                                                                                                               |                                Version                                 |                                            Comment                                            |
|:------------:|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------:|
| IDE / EDITOR |                                                                                                                                  ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)                                                                                                                                  |                                   -                                    |                                               -                                               |
|  Framework   |                                                                                                                                        ![Spring](https://img.shields.io/badge/springBoot-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)                                                                                                                                         |                                 3.3.2                                  |                                               -                                               |
|   Language   |                                                                                                                                            ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)                                                                                                                                            |                                 JDK 21                                 |                                               -                                               |
|     IaaS     | ![AWS EC2](https://img.shields.io/badge/AWS_EC2-RDS?style=for-the-badge&logo=amazonec2&logoColor=white&logoSize=amg&labelColor=FF9900&color=FF9900) <br> ![Prometheus](https://img.shields.io/badge/Prometheus-E6522C?style=for-the-badge&logo=Prometheus&logoColor=white) <br> ![Grafana](https://img.shields.io/badge/grafana-%23F46800.svg?style=for-the-badge&logo=grafana&logoColor=white) | EC2 : Amazon Linux 2023 AMI <br> Prometheus: 2.54 <br> Grafana: 11.1.4 |                                   ECS Container Monitoring                                    |
|   Database   |                                                                ![AWS RDS](https://img.shields.io/badge/AWS_RDS-RDS?style=for-the-badge&logo=amazonrds&logoColor=white&logoSize=amg&labelColor=527FFF&color=527FFF) <br> ![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white)                                                                |                             MariaDB: 10.11                             |                    Store relational data such as User, Space, Channel, etc                    |
|   Database   |                                                                                                                                          ![MongoDB](https://img.shields.io/badge/mongodb-47A248.svg?style=for-the-badge&logo=mongodb&logoColor=white)                                                                                                                                           |                         MongoDB Atlas: 7.0.12                          |                  Store unstructured data such as Text, DM, Notification, etc                  |
|   Database   |                                                                       ![AWS Elasticache](https://img.shields.io/badge/AWS%20elasticache-C925D1?style=for-the-badge&logo=amazonelasticache&logoColor=white) <br> ![Redis](https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white)                                                                       |                               Redis: 7.1                               |                                    Auth Data Save, Pub/Sub                                    |
|    Record    |                                                                                                                                          ![Notion](https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white)                                                                                                                                           |                                   -                                    |         [Link](https://teamsparta.notion.site/Echo-191b7395737d4a608c2e07bd98c42f2a)          |


[(Back to top)](#table)

<br>

<div id="e">

# 🧱 Project Architecture
<p align="center"> <img src="https://github.com/user-attachments/assets/cea5df8d-ba85-41c7-a86e-1d538ae31d79"> </p>

[(Back to top)](#table)

<br>

<div id="f">

# 📦 Package Structure
```angular2html
src
├─common
│  ├─aop
│  ├─exception
│  │  ├─codes
│  │  └─handler
│  ├─redis
│  ├─s3
│  │  ├─dto
│  │  ├─error
│  │  ├─service
│  │  └─util
│  └─util
├─config
├─domain
│  ├─auth
│  │  ├─dto
│  │  └─error
│  ├─channel
│  │  ├─dto
│  │  ├─entity
│  │  ├─error
│  │  └─repository
│  ├─dm
│  │  ├─dto
│  │  ├─entity
│  │  └─repository
│  ├─friend
│  │  ├─dto
│  │  ├─entity
│  │  ├─error
│  │  └─repository
│  ├─mail
│  ├─notification
│  │  ├─dto
│  │  ├─entity
│  │  └─repository
│  ├─space
│  │  ├─dto
│  │  ├─entity
│  │  ├─error
│  │  └─repository
│  ├─text
│  │  ├─controller
│  │  ├─dto
│  │  ├─entity
│  │  ├─error
│  │  └─repository
│  ├─thread
│  │  ├─dto
│  │  ├─entity
│  │  ├─error
│  │  ├─repository
│  │  └─service
│  ├─user
│  │  ├─dto
│  │  ├─entity
│  │  ├─error
│  │  └─repository
│  └─video
└─security
    ├─config
    ├─jwt
    └─principal
```


[(Back to top)](#table)

<br>

<div id="g">

# 🔑 Environment Variable
```angular2html
MARIADB_ROOT_PASSWORD=root
MARIADB_USER=user
MARIADB_PASSWORD=password
MARIADB_DATABASE=echo
MARIADB_URL=r2dbc:mariadb://localhost:13306/echo
MONGODB_ATLAS_URL=mongodb+srv://{user}:{password}@{db_url}/{db_name}?retryWrites=true&w=majority
MONGODB_URL=mongodb://root:1234@localhost:27017/echo?authSource=admin
JWT_SECRET=24eb4ca6488cef9acf3956342dd0e7f6bbbfd83aff107caecb5179991cc97ace4195ff5f893897c2b6f48ae3415d1f890f6fbb5df02e6cfac962e41efc09cb65
JWT_ACCESS_TIME=18000000
JWT_REFRESH_TIME=18000000
MAIL_USER={sender_email}
MAIL_PASSWORD={sender_email_password}
REDIS_URI=redis://localhost:6379
REDIS_HOST=localhost
REDIS_PORT=6379
AWS_CREDENTIALS_ACCESSKEY={aws_access_key}
AWS_CREDENTIALS_SECRETKEY={aws_secret_key}
AWS_S3_BUCKET_NAME=echo-image
KAKAO_CLIENT_ID={kakao_client_id}
KAKAO_REDIRECT_URI=http://localhost:8080/api/user/kakao/callback
KAKAO_TOKEN_URL=https://kauth.kakao.com/oauth/token
```

[(Back to top)](#table)

<br>

<div id="h">

# 📑 ERD DIAGRAM
<p align="center"> <img src="https://github.com/user-attachments/assets/ab02d81e-51bb-4c58-9afa-39da53d3ed61"> </p>

[(Back to top)](#table)

<br>

<div id="i">

# 🗂️ API Document
<p align="center"> <img src="https://github.com/user-attachments/assets/c822d886-c304-44b7-a93c-3b135377cef9"> </p>


[(Back to top)](#table)
