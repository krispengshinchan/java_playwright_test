println "Hello, Groovy!"
pipeline {
    agent any
    environment {
        // 设置环境变量
        VERSION = '1.0.0'
        DEPLOY_ENV = 'production'
        CREDENTIALS_ID = 'my-docker-creds'
        PATH = 'org/example/TestExampleAPI'
    }
    options {
        timeout(time: 1, unit: 'HOURS')  // 设置超时时间
        disableConcurrentBuilds()         // 禁止并发构建
    }
    stages {
        stage('初始化') {
            steps {
                echo "开始执行流水线构建"
                echo "当前版本: ${VERSION}"
                echo "部署环境: ${DEPLOY_ENV}"
                echo "执行测试路径： ${PATH}"
            }
        }

        stage('代码检出') {
            steps {
                git branch: 'master',
                url: 'https://github.com/krispengshinchan/java_playwright_test.git'
            }
        }

        stage('代码构建') {
            steps {
                sh 'mvn clean package'  // Maven 项目示例
                // 或使用 gradle: sh './gradlew build'
                // 或使用 npm: sh 'npm install && npm run build'
            }
        }

        stage('单元测试') {
            steps {
                sh "mvn test -Dtest=${PATH}"
                // 生成测试报告
                junit '**/target/surefire-reports/*.xml'
            }
        }

        stage('代码扫描') {
            steps {
                // 使用 SonarQube 进行代码分析
                withSonarQubeEnv('sonar-server') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

//         stage('构建Docker镜像') {
//             when {
//                 branch 'master'  // 仅 main 分支执行
//             }
//             steps {
//                 script {
//                     docker.build("your-image:${VERSION}").push()
//                 }
//             }
//         }
//
//         stage('部署到测试环境') {
//             when {
//                 environment name: 'DEPLOY_ENV', value: 'test'
//             }
//             steps {
//                 sh 'kubectl apply -f k8s/deployment-test.yaml'
//             }
//         }
//
//         stage('部署到生产环境') {
//             when {
//                 environment name: 'DEPLOY_ENV', value: 'production'
//                 branch 'main'
//             }
//             steps {
//                 timeout(time: 5, unit: 'MINUTES') {
//                     input message: '确认部署到生产环境？'
//                 }
//                 sh 'kubectl apply -f k8s/deployment-prod.yaml'
//             }
//         }
    }
    post {
        always {
            echo "always清理工作空间..."
//             deleteDir() // 清理工作目录
        }
        success {
            script{
                currentBuild.description = "\n success"
            }
//             slackSend channel: '#ci-cd',
//                      message: "构建成功: ${env.JOB_NAME} ${env.BUILD_NUMBER}"
        }
        failure {
            script{
                    currentBuild.description = "\n failure"
            }
//             slackSend channel: '#ci-cd',
//                      color: 'danger',
//                      message: "构建失败: ${env.JOB_NAME} ${env.BUILD_NUMBER}"
        }
    }
}