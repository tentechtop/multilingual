DROP TABLE IF EXISTS off_product;
CREATE TABLE off_product(
                            `product_id` INT NOT NULL AUTO_INCREMENT  COMMENT '产品ID' ,
                            `product_model` VARCHAR(90) NOT NULL   COMMENT '产品型号;英文名称首字母大写(如:Kwun-B33H)' ,
                            `product_name` VARCHAR(90) NOT NULL   COMMENT '产品名称' ,
                            `product_nickname` VARCHAR(90)    COMMENT '产品昵称' ,
                            `product_desc` VARCHAR(900) NOT NULL   COMMENT '产品描述' ,
                            `img_url` VARCHAR(255) NOT NULL   COMMENT '产品预览图' ,
                            `is_enable` TINYINT NOT NULL  DEFAULT 1 COMMENT '是否可用;0不可用/1可用' ,
                            `is_delete` TINYINT(1) NOT NULL  DEFAULT 0 COMMENT '是否删除;0不删除/1删除' ,
                            `revision` INT NOT NULL   COMMENT '乐观锁' ,
                            `create_by_uid` INT NOT NULL   COMMENT '创建人ID' ,
                            `create_time` DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
                            `update_by_uid` INT NOT NULL   COMMENT '更新人' ,
                            `update_time` DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
                            PRIMARY KEY (product_id)
)  COMMENT = '产品表';


CREATE INDEX idx_product_model ON off_product(product_model);

DROP TABLE IF EXISTS off_product_media;
CREATE TABLE off_product_media(
                                  `media_id` INT NOT NULL AUTO_INCREMENT  COMMENT '产品媒体ID' ,
                                  `product_id` INT NOT NULL   COMMENT '产品ID' ,
                                  `lang` VARCHAR(16) NOT NULL   COMMENT '语言标志' ,
                                  `type` VARCHAR(16) NOT NULL   COMMENT '媒体类型img/video;资源类型' ,
                                  `sort` INT NOT NULL  DEFAULT 0 COMMENT '排序;排序' ,
                                  `img_url` VARCHAR(255)    COMMENT '图片url地址' ,
                                  `video_url` VARCHAR(255)    COMMENT '视频url地址' ,
                                  `alt` VARCHAR(900)    COMMENT '媒体描述' ,
                                  `is_enable` TINYINT(1) NOT NULL  DEFAULT 1 COMMENT '是否可用;0不可用/1可用' ,
                                  `is_delete` TINYINT(1) NOT NULL  DEFAULT 0 COMMENT '是否删除;0不删除/1删除' ,
                                  `create_time` DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
                                  `update_time` DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
                                  PRIMARY KEY (media_id)
)  COMMENT = '产品媒体表';


CREATE INDEX idx_product ON off_product_media(product_id);

DROP TABLE IF EXISTS off_series;
CREATE TABLE off_series(
                           `series_id` INT NOT NULL AUTO_INCREMENT  COMMENT '产品系列ID' ,
                           `series_name` VARCHAR(90) NOT NULL   COMMENT '系列名称' ,
                           `series_desc` VARCHAR(900) NOT NULL   COMMENT '系列描述' ,
                           `img_url` VARCHAR(255)    COMMENT '系列描述图片' ,
                           `is_enable` TINYINT(1) NOT NULL  DEFAULT 1 COMMENT '是否可用;0不可用/1可用' ,
                           `is_delete` TINYINT(1)   DEFAULT 0 COMMENT '是否删除;0不删除/1删除' ,
                           `create_by_uid` INT NOT NULL   COMMENT '创建人' ,
                           `create_time` DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
                           `update_by_uid` INT NOT NULL   COMMENT '更新人' ,
                           `update_time` DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
                           PRIMARY KEY (series_id)
)  COMMENT = '系列表';

DROP TABLE IF EXISTS off_file;
CREATE TABLE off_file(
                         `file_id` INT NOT NULL AUTO_INCREMENT  COMMENT '文件ID' ,
                         `file_format` VARCHAR(8) NOT NULL   COMMENT '文件格式;如:PDF  DOCX等' ,
                         `file_name` VARCHAR(128) NOT NULL   COMMENT '文件名称;如:Kwun-B33H说明书 产品名称+用途' ,
                         `file_url` VARCHAR(512) NOT NULL   COMMENT '文件url路径' ,
                         `file_desc` VARCHAR(900)    COMMENT '文件描述' ,
                         `file_content` TEXT NOT NULL   COMMENT '文件内容' ,
                         `is_enable` TINYINT(1) NOT NULL  DEFAULT 1 COMMENT '是否可用;0不可用/1可用' ,
                         `is_delete` TINYINT(1) NOT NULL  DEFAULT 0 COMMENT '是否删除;0不删除/1删除' ,
                         `create_by_uid` INT NOT NULL   COMMENT '创建人' ,
                         `create_time` DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
                         `update_by_uid` INT NOT NULL   COMMENT '更新人' ,
                         `update_time` DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
                         PRIMARY KEY (file_id)
)  COMMENT = '文件表';

DROP TABLE IF EXISTS off_product_specs;
CREATE TABLE off_product_specs(
                                  `specs_id` INT NOT NULL AUTO_INCREMENT  COMMENT '规格ID' ,
                                  `product_id` INT NOT NULL   COMMENT '产品ID' ,
                                  `lang` VARCHAR(16) NOT NULL   COMMENT '语言标志' ,
                                  `sort` INT NOT NULL  DEFAULT 0 COMMENT '排序;升序排序 0 1 2 3' ,
                                  `specs_name` VARCHAR(255) NOT NULL   COMMENT '规格名称;参数名称：如外形、续航' ,
                                  `img_url` VARCHAR(255)    COMMENT '规格图标' ,
                                  `is_enable` TINYINT(1) NOT NULL  DEFAULT 1 COMMENT '是否可用;0不可用/1可用' ,
                                  `is_delete` TINYINT(1) NOT NULL  DEFAULT 0 COMMENT '是否删除;0不删除/1删除' ,
                                  PRIMARY KEY (specs_id)
)  COMMENT = '规格表';


CREATE INDEX idx_product ON off_product_specs(product_id);

DROP TABLE IF EXISTS off_product_i18n;
CREATE TABLE off_product_i18n(
                                 `i18n_id` INT NOT NULL AUTO_INCREMENT  COMMENT '国际化ID' ,
                                 `product_id` INT NOT NULL   COMMENT '产品表ID' ,
                                 `lang` VARCHAR(16) NOT NULL   COMMENT '语言标志;语言标志(1zh、2-en)' ,
                                 `product_name` VARCHAR(255) NOT NULL   COMMENT '产品名称' ,
                                 `product_nick_name` VARCHAR(255)    COMMENT '产品昵称' ,
                                 `product_desc` VARCHAR(1024) NOT NULL   COMMENT '产品描述' ,
                                 `img_url` VARCHAR(255) NOT NULL   COMMENT '产品预览图' ,
                                 PRIMARY KEY (i18n_id)
)  COMMENT = '产品国际化表';


CREATE INDEX idx_product ON off_product_i18n(product_id);
CREATE INDEX idx_lang ON off_product_i18n(lang);

DROP TABLE IF EXISTS off_language;
CREATE TABLE off_language(
                             `language_id` INT NOT NULL AUTO_INCREMENT  COMMENT '语言ID' ,
                             `lang` VARCHAR(16) NOT NULL   COMMENT '语言标志;语言标志如（en、zh）' ,
                             `en_name` VARCHAR(32) NOT NULL   COMMENT '语言英文名称' ,
                             `dialect` VARCHAR(90)    COMMENT '方言;语言方言(如西班牙语；方言Español)' ,
                             `flag_img_url` VARCHAR(255)    COMMENT '旗帜' ,
                             `sort` INT NOT NULL  DEFAULT 0 COMMENT '排序字段' ,
                             `is_enable` TINYINT(1) NOT NULL  DEFAULT 1 COMMENT '是否可用' ,
                             `is_delete` TINYINT(1) NOT NULL  DEFAULT 0 COMMENT '是否删除' ,
                             `create_by_uid` INT    COMMENT '创建人' ,
                             `create_time` DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
                             `update_by_uid` INT    COMMENT '更新人' ,
                             `update_time` DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
                             PRIMARY KEY (language_id)
)  COMMENT = '语言表';

DROP TABLE IF EXISTS off_specs_params;
CREATE TABLE off_specs_params(
                                 `params_id` INT NOT NULL AUTO_INCREMENT  COMMENT '规格参数ID' ,
                                 `specs_id` INT NOT NULL   COMMENT '规格ID' ,
                                 `product_id` INT NOT NULL   COMMENT '产品ID' ,
                                 `lang` VARCHAR(16) NOT NULL   COMMENT '语言标志' ,
                                 `sort` INT NOT NULL  DEFAULT 0 COMMENT '排序;升序排序0 1 2 3' ,
                                 `params_title` VARCHAR(255) NOT NULL   COMMENT '参数标题' ,
                                 `value_type` VARCHAR(8) NOT NULL   COMMENT '参数值类型;int、String' ,
                                 `int_value` INT    COMMENT 'int类型参数' ,
                                 `string_value` VARCHAR(255)    COMMENT '字符串类型参数' ,
                                 `is_enable` TINYINT(1) NOT NULL  DEFAULT 1 COMMENT '是否可用' ,
                                 `is_delete` TINYINT(1) NOT NULL  DEFAULT 0 COMMENT '是否删除' ,
                                 PRIMARY KEY (params_id)
)  COMMENT = '规格参数表';


CREATE INDEX idx_specs ON off_specs_params(specs_id);
CREATE INDEX idx_product ON off_specs_params(product_id);
CREATE INDEX idx_specs_product ON off_specs_params(specs_id,product_id);

DROP TABLE IF EXISTS off_product_category;
CREATE TABLE off_product_category(
                                     `product_category_id` INT NOT NULL AUTO_INCREMENT  COMMENT '产品分类ID' ,
                                     `category_id` INT NOT NULL   COMMENT '产品分类ID' ,
                                     `product_id` INT NOT NULL   COMMENT '产品ID' ,
                                     PRIMARY KEY (product_category_id)
)  COMMENT = '产品分类表';


CREATE INDEX idx_product_category ON off_product_category(category_id,product_id);

DROP TABLE IF EXISTS off_tag;
CREATE TABLE off_tag(
                        `tag_id` INT NOT NULL AUTO_INCREMENT  COMMENT '标签ID' ,
                        `tag_name` VARCHAR(90) NOT NULL   COMMENT '标签名称' ,
                        `is_enable` TINYINT(1) NOT NULL  DEFAULT 1 COMMENT '是否可用;0不可用/可用' ,
                        `is_delete` TINYINT(1) NOT NULL  DEFAULT 0 COMMENT '是否删除;0不删除/删除' ,
                        `create_by_uid` INT NOT NULL   COMMENT '创建人' ,
                        `create_time` DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
                        `update_by_uid` INT NOT NULL   COMMENT '更新人' ,
                        `update_time` DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
                        PRIMARY KEY (tag_id)
)  COMMENT = '标签表';

DROP TABLE IF EXISTS off_category;
CREATE TABLE off_category(
                             `category_id` INT NOT NULL AUTO_INCREMENT  COMMENT '分类ID' ,
                             `category_name` VARCHAR(90) NOT NULL   COMMENT '分类名称;页面分类必须在末尾加上页面如首页页面' ,
                             `img_url` VARCHAR(255) NOT NULL   COMMENT '分类图片;分类封面图片' ,
                             `category_desc` VARCHAR(900) NOT NULL   COMMENT '分类描述;描述分类的范围，分类下有哪些产品' ,
                             `is_enable` TINYINT(1) NOT NULL  DEFAULT 1 COMMENT '是否可用' ,
                             `is_delete` TINYINT(1) NOT NULL  DEFAULT 0 COMMENT '是否删除' ,
                             `create_by_uid` INT NOT NULL   COMMENT '创建人' ,
                             `create_time` DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
                             `update_by_uid` INT NOT NULL   COMMENT '更新人' ,
                             `update_time` DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
                             PRIMARY KEY (category_id)
)  COMMENT = '分类表';

DROP TABLE IF EXISTS off_product_series;
CREATE TABLE off_product_series(
                                   `product_series_id` INT NOT NULL AUTO_INCREMENT  COMMENT '产品系列ID' ,
                                   `product_id` INT NOT NULL   COMMENT '产品ID' ,
                                   `series_id` INT NOT NULL   COMMENT '系列ID' ,
                                   PRIMARY KEY (product_series_id)
)  COMMENT = '产品系列表';


CREATE INDEX idx_product_series ON off_product_series(product_id,series_id);

DROP TABLE IF EXISTS off_product_tag;
CREATE TABLE off_product_tag(
                                `product_tag_id` INT NOT NULL AUTO_INCREMENT  COMMENT '产品标签ID' ,
                                `product_id` INT NOT NULL   COMMENT '产品ID' ,
                                `tag_id` INT NOT NULL   COMMENT '标签ID' ,
                                PRIMARY KEY (product_tag_id)
)  COMMENT = '产品标签表';

DROP TABLE IF EXISTS off_product_file;
CREATE TABLE off_product_file(
                                 `product_file_id` INT NOT NULL AUTO_INCREMENT  COMMENT '产品文件ID' ,
                                 `product_id` INT NOT NULL   COMMENT '产品ID' ,
                                 `file_id` INT NOT NULL   COMMENT '文件ID' ,
                                 PRIMARY KEY (product_file_id)
)  COMMENT = '产品文件表';


CREATE INDEX idx_product_file ON off_product_file(product_id,file_id);

DROP TABLE IF EXISTS off_category_i18n;
CREATE TABLE off_category_i18n(
                                  `i18n_id` INT NOT NULL AUTO_INCREMENT  COMMENT '分类国际化ID' ,
                                  `category_id` INT NOT NULL   COMMENT '分类ID' ,
                                  `lang` VARCHAR(16) NOT NULL   COMMENT '语言标志' ,
                                  `category_name` VARCHAR(90) NOT NULL   COMMENT '分类名称' ,
                                  `img_url` VARCHAR(255) NOT NULL   COMMENT '分类图片' ,
                                  `category_desc` VARCHAR(900) NOT NULL   COMMENT '分类描述' ,
                                  PRIMARY KEY (i18n_id)
)  COMMENT = '分类国际化表';

DROP TABLE IF EXISTS off_series_i18n;
CREATE TABLE off_series_i18n(
                                `i18n_id` INT NOT NULL AUTO_INCREMENT  COMMENT '系列国际化ID' ,
                                `series_id` INT NOT NULL   COMMENT '系列ID' ,
                                `lang` VARCHAR(16) NOT NULL   COMMENT '语言标志' ,
                                `series_name` VARCHAR(255) NOT NULL   COMMENT '系列名称' ,
                                `series_desc` VARCHAR(512)    COMMENT '系列描述' ,
                                `img_url` VARCHAR(255)    COMMENT '系列图片' ,
                                PRIMARY KEY (i18n_id)
)  COMMENT = '系列国际化表';

DROP TABLE IF EXISTS off_tag_i18n;
CREATE TABLE off_tag_i18n(
                             `i18n_id` INT NOT NULL AUTO_INCREMENT  COMMENT '标签国际化ID' ,
                             `tag_id` INT NOT NULL   COMMENT '标签ID' ,
                             `tag_name` VARCHAR(90) NOT NULL   COMMENT '标签名称' ,
                             PRIMARY KEY (i18n_id)
)  COMMENT = '标签国际化表';

DROP TABLE IF EXISTS off_news;
CREATE TABLE off_news(
                         `news_id` INT NOT NULL AUTO_INCREMENT  COMMENT '新闻ID' ,
                         `lang` VARCHAR(16) NOT NULL   COMMENT '语言标志' ,
                         `cover_img_url` VARCHAR(255) NOT NULL   COMMENT '新闻封面图片url' ,
                         `news_title` VARCHAR(255) NOT NULL   COMMENT '新闻标题' ,
                         `html_text` MEDIUMTEXT NOT NULL   COMMENT '新闻内容;seo优化内容' ,
                         `text` TEXT NOT NULL   COMMENT '新闻内容seo' ,
                         `sort` INT    COMMENT '排序字段' ,
                         `is_enable` TINYINT(1) NOT NULL  DEFAULT 1 COMMENT '是否可用;0不可用/1可用' ,
                         `is_delete` TINYINT(1) NOT NULL  DEFAULT 0 COMMENT '是否删除;0不删除/1删除' ,
                         `create_by_uid` INT    COMMENT '创建人' ,
                         `create_time` DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
                         `update_by_uid` INT    COMMENT '更新人' ,
                         `update_time` DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
                         PRIMARY KEY (news_id)
)  COMMENT = '新闻表';




DROP TABLE IF EXISTS off_modules_item;
CREATE TABLE off_modules_item(
                                 `item_id` INT NOT NULL AUTO_INCREMENT  COMMENT '模块ID' ,
                                 `modules_id` INT NOT NULL   COMMENT '模块ID' ,
                                 `lang` VARCHAR(16) NOT NULL   COMMENT '语言标志' ,
                                 `sort` INT NOT NULL   COMMENT '排序;0 1 2 3 4 5 升序排序' ,
                                 `img_url` VARCHAR(255)    COMMENT '图片url路径' ,
                                 `poster` VARCHAR(255)    COMMENT '视频预览封面' ,
                                 `video_url` VARCHAR(255)    COMMENT '视频url路径' ,
                                 `title1` VARCHAR(255)    COMMENT '标题1' ,
                                 `title2` VARCHAR(255)    COMMENT '标题2' ,
                                 `desc1` VARCHAR(255)    COMMENT '描述1' ,
                                 `desc2` VARCHAR(255)    COMMENT '描述2' ,
                                 `url1` VARCHAR(255)    COMMENT '目标地址1' ,
                                 `url1_title` VARCHAR(255)    COMMENT '路由标题1;如:了解更多，立即购买，观看视频' ,
                                 `url2` VARCHAR(255)    COMMENT '目标地址2' ,
                                 `url2_title` VARCHAR(255)    COMMENT '路由标题2' ,
                                 `create_time` DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
                                 `update_time` DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
                                 PRIMARY KEY (item_id)
)  COMMENT = '模块元素表';


CREATE INDEX idx_module ON off_modules_item(modules_id);

DROP TABLE IF EXISTS off_modules;
CREATE TABLE off_modules(
                            `modules_id` INT NOT NULL AUTO_INCREMENT  COMMENT '模块ID' ,
                            `page_id` INT NOT NULL   COMMENT '页面ID' ,
                            `sort` INT NOT NULL   COMMENT '排序字段升序' ,
                            `position` VARCHAR(255)    COMMENT '位置(组件名称)' ,
                            `title` VARCHAR(300)    COMMENT '标题' ,
                            `sub_title` VARCHAR(512)    COMMENT '副标题' ,
                            `create_time` DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
                            `update_time` DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
                            PRIMARY KEY (modules_id)
)  COMMENT = '模块表';


CREATE INDEX idx_page ON off_modules(page_id);

DROP TABLE IF EXISTS off_faq;
CREATE TABLE off_faq(
                        `faq_id` INT NOT NULL AUTO_INCREMENT  COMMENT '常见问题与回答ID' ,
                        `sort` INT NOT NULL  DEFAULT 0 COMMENT '排序字段升序' ,
                        `faq_title` VARCHAR(255) NOT NULL   COMMENT '问题概述' ,
                        `faq_question` VARCHAR(512) NOT NULL   COMMENT '问题详细' ,
                        `faq_answers` MEDIUMTEXT NOT NULL   COMMENT '问题解答' ,
                        `like_number` INT   DEFAULT 18 COMMENT '点赞数量' ,
                        `is_delete` TINYINT(1) NOT NULL  DEFAULT 0 COMMENT '是否删除;0不删除/1删除' ,
                        `create_time` DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
                        `update_time` DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
                        PRIMARY KEY (faq_id)
)  COMMENT = '常见问题解答与通知公告表';

DROP TABLE IF EXISTS off_faq_i18n;
CREATE TABLE off_faq_i18n(
                             `i18n_id` INT NOT NULL AUTO_INCREMENT  COMMENT '国际化ID' ,
                             `faq_id` INT NOT NULL   COMMENT '常见问题ID' ,
                             `lang` VARCHAR(16) NOT NULL   COMMENT '语言标志' ,
                             `faq_title` VARCHAR(255) NOT NULL   COMMENT '常见问题概述' ,
                             `faq_question` MEDIUMTEXT NOT NULL   COMMENT '常见问题详细' ,
                             `faq_answers` MEDIUMTEXT NOT NULL   COMMENT '常见问题解答' ,
                             PRIMARY KEY (i18n_id)
)  COMMENT = '常见问题与回答国际化表';


CREATE INDEX idx_faq ON off_faq_i18n(faq_id);

DROP TABLE IF EXISTS off_faq_category;
CREATE TABLE off_faq_category(
                                 `faq_category_id` INT NOT NULL AUTO_INCREMENT  COMMENT '常见问题与回答分类ID' ,
                                 `faq_id` INT NOT NULL   COMMENT '常见问题ID' ,
                                 `category_id` INT NOT NULL   COMMENT '分类ID' ,
                                 PRIMARY KEY (faq_category_id)
)  COMMENT = '常见问题与回答分类表';


CREATE INDEX idx_faq_category ON off_faq_category(faq_id,category_id);

DROP TABLE IF EXISTS off_faq_tag;
CREATE TABLE off_faq_tag(
                            `faq_tag_id` INT NOT NULL AUTO_INCREMENT  COMMENT '常见问题标签ID' ,
                            `faq_id` INT NOT NULL   COMMENT '常见问题ID' ,
                            `tag_id` INT NOT NULL   COMMENT '标签ID' ,
                            PRIMARY KEY (faq_tag_id)
)  COMMENT = '常见问题标签表';

DROP TABLE IF EXISTS off_course;
CREATE TABLE off_course(
                           `course_id` INT NOT NULL AUTO_INCREMENT  COMMENT '课程Id' ,
                           `course_name` VARCHAR(90) NOT NULL   COMMENT '课程名称' ,
                           `course_desc` VARCHAR(512) NOT NULL   COMMENT '课程描述' ,
                           `course_cover` VARCHAR(255) NOT NULL   COMMENT '课程封面Url' ,
                           `sort` INT NOT NULL  DEFAULT 0 COMMENT '排序;升序排序' ,
                           `is_enable` TINYINT(1) NOT NULL   COMMENT '是否可用' ,
                           `is_delete` TINYINT(1) NOT NULL   COMMENT '是否删除' ,
                           `create_time` DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
                           `update_time` DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
                           PRIMARY KEY (course_id)
)  COMMENT = '课程';

DROP TABLE IF EXISTS off_course_category;
CREATE TABLE off_course_category(
                                    `course_category_id` INT NOT NULL AUTO_INCREMENT  COMMENT '课程分类ID' ,
                                    `course_id` INT NOT NULL   COMMENT '课程ID' ,
                                    `category_id` INT NOT NULL   COMMENT '分类ID' ,
                                    PRIMARY KEY (course_category_id)
)  COMMENT = '课程分类';


CREATE INDEX idx_course_category ON off_course_category(course_id,category_id);

DROP TABLE IF EXISTS off_course_video;
CREATE TABLE off_course_video(
                                 `video_id` INT NOT NULL AUTO_INCREMENT  COMMENT '课程视频ID' ,
                                 `course_id` INT NOT NULL   COMMENT '课程ID' ,
                                 `lang` VARCHAR(16) NOT NULL   COMMENT '语言标志' ,
                                 `title` VARCHAR(90) NOT NULL   COMMENT '视频标题' ,
                                 `desc` VARCHAR(255) NOT NULL   COMMENT '视频描述' ,
                                 `poster` VARCHAR(255) NOT NULL   COMMENT '封面Url' ,
                                 `vidoe_url` VARCHAR(255) NOT NULL   COMMENT '视频URL' ,
                                 `views_content` INT NOT NULL  DEFAULT 167 COMMENT '播放次数' ,
                                 `like_content` INT NOT NULL  DEFAULT 124 COMMENT '点赞数量' ,
                                 `is_enable` TINYINT(1) NOT NULL  DEFAULT 1 COMMENT '是否可用;0不可用/1可用' ,
                                 `is_delete` TINYINT(1) NOT NULL  DEFAULT 0 COMMENT '是否删除;0不删除/1删除' ,
                                 `create_time` DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
                                 `update_time` DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
                                 PRIMARY KEY (video_id)
)  COMMENT = '课程视频';


CREATE INDEX idx_course ON off_course_video(course_id);

DROP TABLE IF EXISTS off_product_price;
CREATE TABLE off_product_price(
                                  `price_id` INT NOT NULL AUTO_INCREMENT  COMMENT '产品价格ID' ,
                                  `product_id` INT NOT NULL   COMMENT '产品ID' ,
                                  `lang` VARCHAR(16) NOT NULL   COMMENT '语言标志' ,
                                  `price` DECIMAL(24,6) NOT NULL  DEFAULT 0 COMMENT '价格' ,
                                  `currency` VARCHAR(255) NOT NULL   COMMENT '结算货币' ,
                                  `symbol` VARCHAR(255) NOT NULL   COMMENT '结算货币符号￥ $ €' ,
                                  `create_time` DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
                                  `update_time` DATETIME NOT NULL  DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
                                  PRIMARY KEY (price_id)
)  COMMENT = '产品价格表';

DROP TABLE IF EXISTS off_file_category;
CREATE TABLE off_file_category(
                                  `file_category_id` INT NOT NULL AUTO_INCREMENT  COMMENT '文件分类ID' ,
                                  `file_id` INT NOT NULL   COMMENT '文件ID' ,
                                  `category_id` INT NOT NULL   COMMENT '分类ID' ,
                                  PRIMARY KEY (file_category_id)
)  COMMENT = '文件分类表';

DROP TABLE IF EXISTS off_file_i18n;
CREATE TABLE off_file_i18n(
                              `i18n_id` INT NOT NULL AUTO_INCREMENT  COMMENT '国际化ID' ,
                              `file_id` INT NOT NULL   COMMENT '文件ID' ,
                              `lang` VARCHAR(16) NOT NULL   COMMENT '语言标志' ,
                              `file_name` VARCHAR(255) NOT NULL   COMMENT '文件名称' ,
                              `file_url` VARCHAR(512) NOT NULL   COMMENT '文件url地址' ,
                              `file_desc` VARCHAR(900) NOT NULL   COMMENT '文件描述' ,
                              `file_content` TEXT NOT NULL   COMMENT '文件内容' ,
                              PRIMARY KEY (i18n_id)
)  COMMENT = '文件国际化表';


CREATE INDEX idx_file ON off_file_i18n(file_id);

DROP TABLE IF EXISTS off_page;
CREATE TABLE off_page(
                         `page_id` INT NOT NULL AUTO_INCREMENT  COMMENT '页面ID' ,
                         `page_name` VARCHAR(255)    COMMENT '页面名称' ,
                         `page_url` VARCHAR(255)    COMMENT '页面地址表' ,
                         `page_desc` VARCHAR(255)    COMMENT '页面描述;简单描述页面用途' ,
                         `is_delete` TINYINT(1) NOT NULL   COMMENT '是否删除;0不删除/1删除' ,
                         `create_time` DATETIME    COMMENT '创建时间' ,
                         `update_time` DATETIME    COMMENT '更新时间' ,
                         PRIMARY KEY (page_id)
)  COMMENT = '页面表';

DROP TABLE IF EXISTS off_seo;
CREATE TABLE off_seo(
                        `seo_id` INT NOT NULL AUTO_INCREMENT  COMMENT 'seo优化ID' ,
                        `page_id` INT NOT NULL   COMMENT '页面ID' ,
                        `lang` VARCHAR(16) NOT NULL   COMMENT '语言标志' ,
                        `title` VARCHAR(255) NOT NULL   COMMENT 'seo标题' ,
                        `keywords` VARCHAR(1500) NOT NULL   COMMENT 'seo关键词;1个汉字占3个字符，关键词的长度在500字以内' ,
                        `description` VARCHAR(3000) NOT NULL   COMMENT 'seo页面描述;1个汉字占3个字符，描述的长度在1000字以内' ,
                        PRIMARY KEY (seo_id)
)  COMMENT = '页面内容搜索表';


CREATE INDEX idx_page ON off_seo(page_id);

DROP TABLE IF EXISTS off_course_i18n;
CREATE TABLE off_course_i18n(
                                `i18n_id` INT NOT NULL AUTO_INCREMENT  COMMENT '国际化ID' ,
                                `course_id` INT NOT NULL   COMMENT '课程ID' ,
                                `course_name` VARCHAR(255)    COMMENT '课程名称' ,
                                `course_desc` VARCHAR(255)    COMMENT '课程描述' ,
                                `course_cover` VARCHAR(255)    COMMENT '课程封面' ,
                                PRIMARY KEY (i18n_id)
)  COMMENT = '课程国际化表';


CREATE INDEX idx_course ON off_course_i18n(course_id);

DROP TABLE IF EXISTS off_modules_i18n;
CREATE TABLE off_modules_i18n(
                                 `i18n_id` INT NOT NULL AUTO_INCREMENT  COMMENT '国际化ID' ,
                                 `modules_id` INT NOT NULL   COMMENT '模块ID' ,
                                 `lang` VARCHAR(16) NOT NULL   COMMENT '语言标志' ,
                                 `title` VARCHAR(300)    COMMENT '标题' ,
                                 `sub_title` VARCHAR(512)    COMMENT '副标题' ,
                                 PRIMARY KEY (i18n_id)
)  COMMENT = '模块国际化表';


CREATE INDEX idx_modules ON off_modules_i18n(modules_id);

