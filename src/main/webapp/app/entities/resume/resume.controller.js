(function() {
    'use strict';

    angular
        .module('resumeApp')
        .controller('ResumeController', ResumeController);

    ResumeController.$inject = ['$scope', '$state', 'Resume', 'ResumeSearch'];

    function ResumeController ($scope, $state, Resume, ResumeSearch) {
        var vm = this;
        vm.resume = mockData();
/*        vm.loadAll = function() {
            Resume.query(function(result) {
                vm.resume = processResult(result);
            });
        };

        vm.search = function () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ResumeSearch.query({query: vm.searchQuery}, function(result) {
                vm.resume = processResult(result);
            });
        };
        vm.loadAll();*/

        function processResult(result){
            if (result.length === 0) return {};
            var tmp = result[0];
            var resume = {
                info: {
                    'email': tmp.infoEmail,
                    'phone': tmp.infoPhone,
                    'github': tmp.infoGithub,
                    'linkedin': tmp.infoLinkedin
                },
                profile: {
                    'Basic': tmp.profileBasic,
                    'Technique domain': tmp.profileTechniqueDomain,
                    'Software system': tmp.profileSoftwareSystem,
                    'Multi-branch experience': tmp.profileMultibranchExperience,
                    'Preferred position': tmp.profilePreferredPosition,
                },
                experiences: [],
                skills: [],
                papers: [],
                educations: []
            };
            angular.forEach(tmp.skills, function(skill, index){
                resume.skills.push(skill.skill);
            });
            angular.forEach(tmp.papers, function(paper, index){
                resume.papers.push(paper.paper);
            });

            return resume;
        }

        function mockData(){
            var resume = {
                info: {
                    'email': 'Lin.qizhong@gmail.com',
                    'phone': '86 136-3647-7046',
                    'github': 'https://github.com/qizhonglin',
                    'linkedin': 'https://cn.linkedin.com/in/qizhong-lin-b286595b'
                },
                profile: {
                    'Basic': '>10 years experiences in healthcare software including 3 years project management, 4 years software architect, >10 years programming and experience of mobile, web and desktop application as full-stack developer',
                    'Technique domain': 'data mining, numeric/signal/graphic/image/text processing, statistics and analysis',
                    'Software system': 'Integrated Health Management, clinical decision support system, ultrasound quantification system (like Philips QLAB), healthcare information solution and service (like PACS, RIS and echo reporting system etc)',
                    'Multi-branch experience': 'Innovative user story with clinical site (e.g. Huaxi hospital, PKU1 hospital), business analysis with market research, functional validation with research project team, user interface and behavior with design team, product with development project team, test team and deployment team',
                    'Preferred position': 'Senior Architecture, Principal Engineer',
                },
                experiences: [
                    {
                        index: 0,
                        position: 'Senior Scientist',
                        company: 'Philips Research Asia - Shanghai',
                        time: 'June 2011 – ',
                        projects: [
                            {
                                index: 0,
                                name: 'Integrated Health Management Cardiac Care Solutions',
                                time: 'Oct. 2014 –',
                                introduction: "to connect level 3 hospital, CHC and home for continuous care giving to Patient, Physicians’ better insight into patients’ health status and overcoming physician shortages with connection to the CHC. ",
                                responsibility: 'as architecture and technical leader design and implement the front-end application (mobile app and web app) and back-end service (RESTful service), as researcher innovate and implement clinical decision support.',
                                platform: 'Eclipse Java; Android Studio Java; Pivotal Cloud Foundry; WebStorm, HTML5 + CSS3 + javascript + AngularJS + Bootstrap',
                                accomplishes: [
                                    'Coordinate clinical site, market team, UI design team, tech team and product team',
                                    'Responsible for the foundation of whole system including architecture design, code guidance, unit & integration test.',
                                    'Responsible as team leader for agile development, quality auditing and product delivery.',
                                    'Responsible as full stack developer for developing main part of backend (RESTful service) as server and frontend (patient portal) as server mobile app, code review frontend physician portal from India team, and integrate the whole system.',
                                    'The system is successfully deployed into PKU1'
                                ]
                            },
                            {
                                index: 1,
                                name: 'Healthcare big data analysis',
                                time: 'June. 2013 – Dec. 2014',
                                introduction: "develop data analytic solution or tool for the service provider and hospitals to analyze the data from PCI registry in the CNCDR. ",
                                responsibility: '1.Dig out use case via communication with clinical site and marketing site. 2. Develop and implement innovative algorithm 3. Coordinate with global team like Philips Design Netherland, Philips Development Netherland and Philips Business Unit (BU) China for project cooperation. 4. Responsible for transferring research result to product and how to implement and integrate research result into existing product',
                                platform: 'Eclipse, Java; Matlab IDE, Matlab; R Studios, R; WebStorm & Grunt, HTML5 + CSS3 + javascript + angularjs;',
                                accomplishes: [
                                    'Define use-case and build business models around CNCDR(China National Cardiovascular Data Repository)',
                                    'Explore and validate data analysis and mining for CNCDR, design and develop the architecture of prototype including data pre-processing, data analysis and data visualization.',
                                    'Research result is transferred to BU and launched as product directly because of much attraction from clinical site and multi-department inside Philips.',
                                    'Manage and coordinate multi-team in multi-stage including clinical site, market research, design team, development project team, test team and deployment team'
                                ]
                            },
                            {
                                index: 2,
                                name: 'NLP (Natural language processing) in China for cardiac application',
                                time: 'Oct.2013–Oct.2014',
                                introduction: "enable the secondary usage of clinical data deposited in unstructured/semi-structured clinical documents, focusing on the use case on abstracting data from history medical records to populate Chinese cardiovascular database",
                                responsibility: '1. Dig out use case via communication with clinical site, marketing site and other research project. 2. Responsible for integration of NLP algorithm via web service. 3. Coordinate with China team like Philips Design China and Philips Business Unit China for project cooperation. 4. Support BU for the stress test of web service. ',
                                platform: 'Eclipse Python; WebStorm, HTML5+CSS3+javascript+ExtJS; JMeter',
                                accomplishes: [
                                    'Define use-case and build business models around echo reporting system and CNCDR',
                                    'Wrap NLP algorithm via web service like SOAP and REST',
                                    'Stress test for web service via JMeter',
                                    'Support the whole solution for NLP including machine-assisted annotation, model training and prediction via CRF++, design and develop Web Service for integration of NLP module into echo reporting system, stress test for Web Service. ',
                                    'Develop the web platform to manage NLP research cycle including machine-assisted annotation, model training and prediction via CRF++, and model evaluation.'
                                ]
                            },
                            {
                                index: 3,
                                name: 'Ultrasound Breast Screening with BIRADS (Breast Imaging Reporting and Data System) for China',
                                time: 'June 2011 – June. 2013',
                                introduction: "research and develop Ultrasound Breast CDS (clinical decision support) for China including three main functions: similar case retrieval, likelihood and BI-RADS report auto-filling, the project aims to assist the inexperienced clinical doctor to make more accuracy diagnosis via cart system, train them via off-line training tool and strive for the standardization of BI-RADS terminology in China",
                                responsibility: 'leader of joint project between Philip Research China and Philips Research North American, communicate with clinical site, solution site and market site for user case, read related papers, design and develop innovative algorithm related to project, design and implement the software prototype, conduct the clinical trial, write the related documents. Another task is to improve and optimize Philips Ultrasound software product in China. ',
                                platform: 'Microsoft Visual Studio.NET 2010, C/C++/C#/WPF; Matlab IDE, Matlab',
                                accomplishes: [
                                    'Dissect product application software named QLAB from Ultrasound Department Philips, responsible for QLAB improvement and function integration like CDS and medical image segmentation, meanwhile, write and arrange the related documents for the global team.',
                                    'Develop and implement software prototype of Ultrasound Breast CDS including software system architecture design, workflow design, GUI, database and back-door algorithm design and development. ',
                                    'The prototype and algorithm performance are out of expectation, project team obtains more funding. Meanwhile, thanks to frequent communication with clinical doctor, the prototype for CDS observer study is efficient. The prototype for BI-RADS structured report contributes to the standard of Ultrasound Breast Screening and diagnosis in China and released into ultrasound workstation',
                                    'Research and develop the innovative algorithm for ultrasound breast CDS including lesion segmentation, feature extraction and machine learning. Write corresponding patent and paper. The developed segmentation is transferred and integrated into Philips Ultrasound software QLab.  ',
                                    'Develop and implement toolkits to support global team to process the data from the clinical site efficiently. Such as ultrasound lesion segmentation system and de-identification algorithm for private information removal of Patient data.'
                                ]
                            }
                        ]
                    },
                    {
                        index: 1,
                        position: 'Senior Engineer ',
                        company: 'R&D Centre Harman -Shanghai',
                        time: 'May 2010 – June 2011',
                        projects: [
                            {
                                index: 0,
                                name: 'Augmented Navigation Unit - Multi-Lane Detection and Tracker',
                                time: '',
                                introduction: "develop and implement smart and real-time multi-lane detection and tracker, and integrated into augmented navigation system",
                                responsibility: 'read related papers and cooperate with global team members about algorithm, implement main part of algorithm as core code with complete document, responsible for the whole large-scale software and module integration, cooperate with team members of hardware department for some algorithm implementation in FPGA, write the related documents.',
                                platform: 'Microsoft Visual Studio.NET 2008, C/C++',
                                accomplishes: [
                                    'Implement and optimize general image process module related to project such as Canny Detector, Probabilistic Hough Line Transformation, Kalman Filter, K-Mean Classifier and other algorithms of paper based on the Framework of C++ code from HarmanRD German.',
                                    'Dissect the large-scale application software named Augmented Navigation from HarmanRD German, integrate multi-lane detection and tracker into Augmented Navigation. '
                                ]
                            }
                        ]
                    },
                    {
                        index: 2,
                        position: 'Researcher ',
                        company: 'R&D Centre Hitachi - Shanghai',
                        time: 'March 2009 –May 2010',
                        projects: [
                            {
                                index: 0,
                                name: '3D visualization of medical image data',
                                time: '',
                                introduction: "to develop and implement real-time 3D visualization of large-scale medical image data, including MIP (maximum intensity projection), and VR (volume rendering). ",
                                responsibility: 'develop real-time MIP algorithm on multi-core CPU, and implement it with software optimization method such as SSE and coarse-grained multi-threading parallel programming; develop real-time MIP and VR in ray-casting algorithm, and implement it with fine-grained multi-threading parallel programming in CUDA architecture. Evaluate the developed algorithm, Code, Write the related reports and documents.',
                                platform: 'Microsoft Visual Studio.NET 2005, C/C++; nVidia CUDA',
                                accomplishes: [
                                    'About real-time MIP in multi-core CPU, under the condition of high image quality, compared with the result of cooperated partner Shanghai Jiaotong University, huge auxiliary memory is reduced and independent of input data, and speed-up rate is about 6, experiment result(about 5 frames per-second) surpass expectation of specification, Hitachi medical merge it in the next generation product',
                                    'About real-time MIP in ray-casting algorithm in GPU, the result is up to expectation of specification ( the speed-up rate of GPU-CPU is more than 10), and finish the task ahead of one month, make the solid foundation for next step OpenCL application',
                                    'Implement simple VR in ray-casting algorithm in CPU and GPU, and optimize it with general software optimization ',
                                    'Design and develop 3D Visualization system of medical image data for demo, including MFC UI, 2D display in three standard planes, 3D Visualization (VR, MIP, MPR&CPR), the skill including  software optimization, DLL and GPU programming'
                                ]
                            }
                        ]
                    },
                    {
                        index: 3,
                        position: 'Engineer ',
                        company: 'R&D Centre INFINITT -Seoul',
                        time: 'July 2007 – March 2009',
                        projects: [
                            {
                                index: 0,
                                name: 'Rapid Diagnosis Software Unit - The Analysis of CT cardiac vessel',
                                time: '',
                                introduction: 'develop high performance computer-aided diagnosis system of various tissues in CT images, which can provide accurate and reliable diagnostic second opinion for physicians, the objective of the Analysis of CT cardiac vessel is to analyze and quantify vessel in CT images for such as stenosis analysis. ',
                                responsibility: 'In charge of one module of company product Rap-diagnosis, improving semi-auto cardiac vessel segmentation to full-auto cardiac vessel segmentation named cardiac vessel seed points detection. Evaluate the developed algorithm, Code, Write the related reports and documents.',
                                platform: 'Microsoft Visual Studio.NET 2005, C/C++; ',
                                accomplishes: [
                                    'Detect aortic root centre and LM&RCA seed points for fully automatic vessel tracking, algorithm test result proves better than Korean colleague, applied to company product and improve company semi-auto product to full-auto product',
                                    'Segment vessel left and right borders to measure vessel diameter for stenosis analysis'
                                ]
                            },
                            {
                                index: 1,
                                name: 'PACS Unit - MPR & CPR of 3D visualization',
                                time: '',
                                introduction: 'Implement and optimize medical image 3D visualization MPR&CPR(multi-plane reconstruction & curved-plane reconstruction)',
                                responsibility: 'In charge of one module of company product PACS, improving the high quality and speed of medical image 3D visualization, develop real-time MPR&CPR in multi-core CPU, and implement it with software optimization method such as SSE and coarse-grained multi-threading parallel programming. Evaluate the developed algorithm, code, write the related reports and documents.',
                                platform: 'ditto ',
                                accomplishes: [
                                    'Develop and optimize real-time MPR&CPR in multi-core CPU, the result surpasses expectation of specification (42>36 fps in tri-linear mode and 12>9 fps in cubic mode), and finish the task ahead of two-weeks. Furthermore, to show the result, simple user interface demo is implemented with OpenGL',
                                    'Obtain the honorary title of annual best staff and monthly. '
                                ]
                            }
                        ]
                    },
                    {
                        index: 4,
                        position: 'Researcher ',
                        company: 'BME Lab Fudan Univ.-Shanghai',
                        time: 'Sep. 2004 – July 2007',
                        projects: [
                            {
                                index: 0,
                                name: 'Computerized aided diagnosis of ultrasonic breast tumor (The National Basic Research Program of China (054119612))',
                                time: '',
                                introduction: 'develop high performance computer-aided diagnosis system of breast tumors in ultrasonic images, which can provide accurate and reliable diagnostic second opinion for physicians to distinguish benign breast lesions from malignant ones',
                                responsibility: 'In charge of whole project and develop main algorithm modules including noise removal, tumor segmentation, feature analysis and pattern classification, then make improvement on each part, work jointly with Shanghai Huashan Hospital and Shanghai Sixth People Hospital to reach objectives.',
                                platform: 'Matlab IDE, Matlab',
                                accomplishes: [
                                    'Design and develop CAD system of ultrasonic breast tumor',
                                    'Study and make improvement on each module',
                                    'Publish six papers of first author',
                                    'Guide 6 undergraduates design',
                                    'Participated in 8th annual conference of Chinese journal of scientific instrument and giving oral presentation “Feature Analysis of Ultrasound Breast Tumor Images”'
                                ]
                            }
                        ]
                    }
                ],
                skills: [
                    'Have solid background in <strong>Data Mining, Machine Learning, Image/Graphic/Text Processing, Signal Processing & Numerical Analysis, and Pattern Recognition</strong>, familiar with the principle of Medical Ultrasound and CT. ',
                    'Experience of <strong>mobile, web and desktop application as full stack developer</strong>. More than <strong>4 years software architecture</strong>, master most of architecture pattern like micro-service, monolithic, layer, pipes/filters, MVC, MVVM etc. Master most of Pattern Design, master Data Structure & Algorithm',
                    'Experience of <strong>Rich Client</strong>. For mobile application, experience of <strong>Android</strong> framework (less experience in <strong>iOS</strong> and <strong>hybrid</strong> application). For web application, experience of <strong>AngularJS+Bootstrap</strong> /Sencha. For desktop application, experience of .NET platform <strong>WPF</strong>, Win-form, MFC and Qt application. ',
                    'Experience of <strong>Server</strong>. In backend for mobile and web application, experience of <strong>spring</strong>(java), <strong>pyramid</strong> (python), and <strong>express</strong>(javascript). In database, experience of NoSQL(<strong>MongoDB</strong>), SQL (<strong>MySQL and PostgreSQL</strong>), SQLite and Redis etc;',
                    'Experience of <strong>Software Optimization</strong>, such as SSE and coarse-grained multi-threading data parallel programming, experience of <strong>GPU Programming</strong>, such as Nvidia CUDA tools and fine-grained multi-threading data parallel programming for GPGPU, have knowledge of OpenCL',
                    'Experience of <strong>3 years Javascript, 4 years Java and Python, 5 years C/C++/C#, 3 years Matlab</strong>, some experience of Objective-C, Swift, Scala, Groovy, R and Tcl/TK. ',
                    'CET level 6, participate in National English Contest for College Student (NECCS) and obtain 2nd Prize, work well with a multi-cultural and diverse work force, experience of more than two years <strong>work oversea</strong>(including Korea, Japan, India, America and Netherland).'
                ],
                papers: [
                    '<strong>Qizhong Lin</strong>, Jianguo Yu, Nuan Zhao, Weiqi Wang, Yi Wang and Yaqing Chen,. “Feature Analysis of Ultrasound Breast Tumor Images”, Chinese Journal of Scientific Instrument, 2006, 27(6)',
                    '<strong>Qizhong Lin</strong>, Jianguo Yu, Yaqing Chen, Weiqi Wang and Yi Wang. “Research on Pattern Classification Methods for Ultrasound Breast Tumor Images”, Shanghai Medical Imaging, 2006, 15(2)',
                    '<strong>Qizhong Lin</strong>, Jianguo Yu and Yi Wang. “Boundary Detection of Ultrasonic Breast Tumor Image”, Chinese Journal of Medical Imaging Technology, 2007, 23(10)',
                    '<strong>Qizhong Lin</strong>, Jianguo Yu and Weiqi Wang. “Speckle-Constrained Nonlinear Anisotropic Diffusion”, Journal of Fudan University Natrural Science, 2008, 47(1)	',
                    'Yiming Zhong, <strong>Qizhong Lin</strong>, Jianguo Yu, Haihong Xue and Sun Kun. “Fuzzy Clustering Segmentation for 3D Echocardiographic Images”, Shanghai Bio-medical engineering. 2007, 2',
                    '及川道雄;吉田英惠;永尾朋洋;高江涛;<strong>林其忠</strong>;韩颖婕, “图像处理方法和图像处理装置”, 申请号: 201010163949.5. 公开号:CN102222352A',
                    '<strong>QiZhong Lin</strong>, Xin LIU, Lilla BOROCZKY, Jingping Xu, Kai Huang, “Segmentation of breast lesions in ultrasound images”, 申请号:2012P01995WOUS.公开号:',
                    'Xin LIU; Yuechen QIAN; Lilla BOROCZKY; <strong>QiZHONG Lin</strong>; George Huang. “Intelligent computer-guided structured reporting for efficiency and clinical decision support”, 申请号: 2012P02062US01.公开号:',
                    '<strong>QiZhong Lin</strong>, Sheena Liu, Shyam Sundar Parajuly, Yinhui Deng, Lilla Boroczky, Sainan Fu, Ying Wu, Yulan Pen, “Ultrasound lesion segmentation using clinical knowledge-driven constrained level set”, EMBC2013',
                    'Yinhui Deng, <strong>Qizhong Lin</strong>, Lilla BOROCZKY, Michael Chun-chieh LEE, Ying WU,”Extraction of information from an image and inclusion thereof in a clinical report”. 申请号:2013P01176WOUS.公开号:',
                    'CHAN, Cyrus; Chiau, Choo Chiap; <strong>Lin, Qi Zhong</strong>; Bont, Jeanne de, Laute, Niels; Gosselink, Jurrien. “An interactive visualization method for clinical process time improvement analysis”, 申请号: 2014PF01094.公开号:',
                    'CHAN, Cyrus; Chiau, Choo Chiap; <strong>Lin, Qi Zhong</strong>; “A quantitative method to prioritize activities for time improvement in clinical processes”, 申请号: 2014PF01094.公开号:',
                    'Chiau, Choo Chiap; <strong>Lin, Qi Zhong</strong>; CHAN, Cyrus; Jia, Yugang. “System and method to automatically generate multi-variable correlation and association on data/registry in healthcare information system”. 申请号: 2014PF01095; 2014PF01096公开号:'
                ],
                educations: [
                    {
                        major: 'MS in Bio-medical Engineering',
                        university: 'Fudan University',
                        time: 'Sep. 2004 – July. 2007'
                    },
                    {
                        major: 'BS in Electrical Technology',
                        university: 'Fujian Normal University',
                        time: 'Sep. 1999 - July. 2004'
                    }
                ]
            };
            return resume;
        }

        activate() ;

        function activate() {
            $(function(){
                var body = $('body');
                body.scrollspy({target: ".bs-docs-sidebar"});
                $(window).on("load", function(){
                    $('body').scrollspy("refresh");
                });

                $(".sidebar").click(function (a) {
                    a.preventDefault();
                });

                setTimeout(function () {
                    var sidebar = $(".bs-docs-sidebar");
                    sidebar.affix({
                        offset: {
                            top: function () {
                                var c = sidebar.offset().top, d = parseInt(sidebar.children(0).css("margin-top"), 10), e = $(".bs-docs-nav").height();
                                return this.top = c - d - e;
                            },
                            bottom: function () {
                                return this.bottom = $(".bs-docs-footer").outerHeight(!0);
                            }
                        }
                    })
                }, 100) ;
            });
        }
    }
})();
