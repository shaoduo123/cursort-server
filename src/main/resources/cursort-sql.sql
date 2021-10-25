/*
 Navicat Premium Data Transfer

 Source Server         : mysql-conn
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : cursort

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 25/10/2021 22:28:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '文件名',
  `father` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '父文件',
  `user_id` bigint DEFAULT NULL COMMENT '所属用户',
  `path` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机储存的相对路径',
  `type` varchar(20) DEFAULT NULL COMMENT '文件类型：文件夹，png，xls，?',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `size` varchar(20) DEFAULT NULL COMMENT '大小',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of file
-- ----------------------------
BEGIN;
INSERT INTO `file` VALUES ('0357874980d94292a0510ee68e879a8d', 'file_word.png', 'c92f6026d0544585a9bae56723742369', 1, 'root/1/files/文件图标素材/file_word.png', 'image/png', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('041ef9f65401410780e1839456cbc0c8', 'c5', '127ac05895ee42cb99e3934d98a6409a', 1, 'root/1/files/c5/', 'FOLDER', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('1017326df43c490698179f31799c6482', '李晓飞.png', '625b8d0d9f1145849ecbcfff1b5c882a', 1, 'root/1/files/bbb/one/李晓飞.png', 'image/png', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('1094d538ab1640d29f98df3efb7dc2cd', 'c46e6c2cac5a46e09e85621b65f9f22e 2.PNG', '2cbaa1bcd7e84e5f906e3aae449fde5a', 1, 'root/1/files/bbb/c46e6c2cac5a46e09e85621b65f9f22e 2.PNG', 'image/png', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('127ac05895ee42cb99e3934d98a6409a', '/', NULL, 1, 'root/1/files/', 'FOLDER', '2021-09-09 13:51:754', NULL);
INSERT INTO `file` VALUES ('15d7eb52e2374373aece78865e229b75', 'WechatIMG64.jpeg', '1fee18f1a94e4774b2cc2c7878cbf368', 1, 'root/1/files/c2/WechatIMG64.jpeg', 'image/jpeg', '2021-10-11 12:36:38', '1.11KB');
INSERT INTO `file` VALUES ('174038acd68c4345aac4118bfa279a66', 'bbb', 'e70bab01761a4e6b8b780d72897f4046', 12, 'root/12/files/bbb/', 'FOLDER', '2021-10-07 22:21:59', '');
INSERT INTO `file` VALUES ('17849558ec6242c983ab64471418a47e', '马焱.png', '625b8d0d9f1145849ecbcfff1b5c882a', 1, 'root/1/files/bbb/one/马焱.png', 'image/png', '2021-10-07 22:21:59', '3.8KB');
INSERT INTO `file` VALUES ('182259024f79408884901152a3f6c473', 'file_png.png', '82e65df4efb442e6a5cdbec60bac9a34', 1, 'root/1/files/c2/shaoduo/file_png.png', 'image/png', '2021-10-07 13:31:34', '3.67KB');
INSERT INTO `file` VALUES ('1a0e3b248f224566b4aece0ea1715578', 'file_jpg.png', '82e65df4efb442e6a5cdbec60bac9a34', 1, 'root/1/files/c2/shaoduo/file_jpg.png', 'image/png', '2021-10-07 13:32:15', '3.71KB');
INSERT INTO `file` VALUES ('1ade707df8b84d35988a070e5167277b', '信息资产.png', '127ac05895ee42cb99e3934d98a6409a', 1, 'root/1/files/信息资产.png', 'image/png', '2021-10-14 11:27:04', '3.67KB');
INSERT INTO `file` VALUES ('1bc9dfa2088345e78b4cb23eb8495fde', 'file_video.png', '82e65df4efb442e6a5cdbec60bac9a34', 1, 'root/1/files/c2/shaoduo/file_video.png', 'image/png', '2021-10-07 13:32:17', '1.11KB');
INSERT INTO `file` VALUES ('1de9ff81e1d0472ea23d2ce538a1953c', 'bg3.jpg', '127ac05895ee42cb99e3934d98a6409a', 1, 'root/1/files/bg3.jpg', 'image/jpeg', '2021-10-11 12:29:08', '1.11KB');
INSERT INTO `file` VALUES ('1fee18f1a94e4774b2cc2c7878cbf368', 'c2', '127ac05895ee42cb99e3934d98a6409a', 1, 'root/1/files/c2/', 'FOLDER', '2021-09-09 13:51:754', NULL);
INSERT INTO `file` VALUES ('2150c5891f2f4d5a86c443705c27b0d0', 'file_music.png', 'c92f6026d0544585a9bae56723742369', 1, 'root/1/files/文件图标素材/file_music.png', 'image/png', '2021-10-07 22:21:59', '1.11KB');
INSERT INTO `file` VALUES ('2c3ab6b41d724a70b2522c2efc1f7216', '搜索.png', '82e65df4efb442e6a5cdbec60bac9a34', 1, 'root/1/files/c2/shaoduo/搜索.png', 'image/png', '2021-10-07 13:31:46', '1.11KB');
INSERT INTO `file` VALUES ('2cbaa1bcd7e84e5f906e3aae449fde5a', 'newB', '127ac05895ee42cb99e3934d98a6409a', 0, 'root/1/files/bbb/', 'FOLDER', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('3683b8a98336407eb480828bfc744450', '96副本.jpg', '1fee18f1a94e4774b2cc2c7878cbf368', 1, 'root/1/files/c2/96副本.jpg', 'image/jpeg', '2021-10-07 22:21:59', '1.11KB');
INSERT INTO `file` VALUES ('3aa178dce8bc4c60b2b7be71f49e0941', '未标题-1.png', '625b8d0d9f1145849ecbcfff1b5c882a', 1, 'root/1/files/bbb/one/未标题-1.png', 'image/png', '2021-10-07 22:21:59', '1.11KB');
INSERT INTO `file` VALUES ('40902a39d38648b5b5578018474a8c46', 'WechatIMG8.jpeg', '625b8d0d9f1145849ecbcfff1b5c882a', 1, 'root/1/files/bbb/one/WechatIMG8.jpeg', 'image/jpeg', '2021-10-07 22:21:59', '1.11KB');
INSERT INTO `file` VALUES ('44309ba0c5ae4af999b89a15198ffa81', 'abcabb', '127ac05895ee42cb99e3934d98a6409a', 1, 'root/1/files/abcabb/', 'FOLDER', NULL, NULL);
INSERT INTO `file` VALUES ('4729c535685d40a28e378c273982f84c', 'WechatIMG64.jpeg', '127ac05895ee42cb99e3934d98a6409a', 1, 'root/1/files/WechatIMG64.jpeg', 'image/jpeg', '2021-10-13 16:24:36', '1.11KB');
INSERT INTO `file` VALUES ('489e2df1551f41748036b3c5f2f48661', 'file_ppt.png', '82e65df4efb442e6a5cdbec60bac9a34', 1, 'root/1/files/c2/shaoduo/file_ppt.png', 'image/png', '2021-10-07 13:32:16', '1.11KB');
INSERT INTO `file` VALUES ('48c323231b8f40299c0b6603681cca94', '马焱.png', '625b8d0d9f1145849ecbcfff1b5c882a', 1, 'root/1/files/bbb/one/马焱.png', 'image/png', '2021-10-07 22:21:59', '1.11KB');
INSERT INTO `file` VALUES ('492d17559ad54421a0768a0f26742fff', '下载 (1).png', '1fee18f1a94e4774b2cc2c7878cbf368', 1, 'root/1/files/c2/下载 (1).png', 'image/png', '2021-10-07 22:21:59', '1.11KB');
INSERT INTO `file` VALUES ('4a45e3843310444db66397e1c64c0b73', '综合素质评价.png', '127ac05895ee42cb99e3934d98a6409a', 1, 'root/1/files/综合素质评价.png', 'image/png', '2021-10-07 22:21:59', '1.11KB');
INSERT INTO `file` VALUES ('50c5deefea9f4607b21b016b71aaaf0f', 'c46e6c2cac5a46e09e85621b65f9f22e.PNG', '2cbaa1bcd7e84e5f906e3aae449fde5a', 1, 'root/1/files/bbb/c46e6c2cac5a46e09e85621b65f9f22e.PNG', 'image/png', '2021-10-07 22:21:59', '1.11KB');
INSERT INTO `file` VALUES ('5c590c3f1ef648589b731650f42b9854', 'file_txt.png', '82e65df4efb442e6a5cdbec60bac9a34', 1, 'root/1/files/c2/shaoduo/file_txt.png', 'image/png', '2021-10-07 13:32:17', '1.11KB');
INSERT INTO `file` VALUES ('5e67d8aa2c4149adbea67460982a1ba3', '综合素质评价.png', '127ac05895ee42cb99e3934d98a6409a', 1, 'root/1/files/综合素质评价.png', 'image/png', '2021-10-07 22:21:59', '1.11KB');
INSERT INTO `file` VALUES ('625b8d0d9f1145849ecbcfff1b5c882a', 'one', '2cbaa1bcd7e84e5f906e3aae449fde5a', 1, 'root/1/files/bbb/one/', 'FOLDER', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('64c4146a503447b28a0bd047832bc944', 'file_png.png', 'c92f6026d0544585a9bae56723742369', 1, 'root/1/files/文件图标素材/file_png.png', 'image/png', '2021-10-07 22:21:59', '1.11KB');
INSERT INTO `file` VALUES ('70dd023e50be409baa25925965f0d656', '综合素质评价.png', '127ac05895ee42cb99e3934d98a6409a', 1, 'root/1/files/综合素质评价.png', 'image/png', '2021-10-07 22:21:59', '1.11KB');
INSERT INTO `file` VALUES ('7587d4d20fb942108aad3d981aebc9a9', 'file_excel.png', '82e65df4efb442e6a5cdbec60bac9a34', 1, 'root/1/files/c2/shaoduo/file_excel.png', 'image/png', '2021-10-07 13:32:15', '1.11KB');
INSERT INTO `file` VALUES ('767e7e9fe3e24f17a35fc626fe5b9eba', 'aaa', 'e70bab01761a4e6b8b780d72897f4046', 12, 'root/12/files/aaa/', 'FOLDER', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('7a07117c565f4f5388e0a1e93e220c0c', '贺瑞相.png', '625b8d0d9f1145849ecbcfff1b5c882a', 1, 'root/1/files/bbb/one/贺瑞相.png', 'image/png', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('7a9584f0bfdc40bbaf90c10eeff186f2', 'WechatIMG35.png', '625b8d0d9f1145849ecbcfff1b5c882a', 1, 'root/1/files/bbb/one/WechatIMG35.png', 'image/png', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('7a99163d7c7442e58cdf9a321874648f', 'file_video.png', 'c92f6026d0544585a9bae56723742369', 1, 'root/1/files/文件图标素材/file_video.png', 'image/png', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('7c1b99d56fb345579ca060061e0ebdfc', '崔袁鹤.png', '1fee18f1a94e4774b2cc2c7878cbf368', 1, 'root/1/files/c2/崔袁鹤.png', 'image/png', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('7e32dd84767945a9aa5e2a7855d6d6a8', 'file_txt.png', 'c92f6026d0544585a9bae56723742369', 1, 'root/1/files/文件图标素材/file_txt.png', 'image/png', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('829747450b4741a8b0aa2e8723f2e20f', 'file_music.png', '82e65df4efb442e6a5cdbec60bac9a34', 1, 'root/1/files/c2/shaoduo/file_music.png', 'image/png', '2021-10-07 13:32:16', NULL);
INSERT INTO `file` VALUES ('82e65df4efb442e6a5cdbec60bac9a34', 'shaoduo', '1fee18f1a94e4774b2cc2c7878cbf368', 1, 'root/1/files/c2/shaoduo/', 'FOLDER', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('8744b6cad8ce4ef499ff19dd6375221b', 'file_code.png', 'c92f6026d0544585a9bae56723742369', 1, 'root/1/files/文件图标素材/file_code.png', 'image/png', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('8767bd6f05e44f22b0f4a9533da0b11a', '116.jpeg', '2cbaa1bcd7e84e5f906e3aae449fde5a', 1, 'root/1/files/bbb/116.jpeg', 'image/jpeg', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('87a589693eba428d85f5c564215ce258', '1马焱.png', '625b8d0d9f1145849ecbcfff1b5c882a', 1, 'root/1/files/bbb/one/1马焱.png', 'image/png', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('8b44fdb8937c49f382e6f28f4c54cb7f', 'file_png.png', '82e65df4efb442e6a5cdbec60bac9a34', 1, 'root/1/files/c2/shaoduo/file_png.png', 'image/png', '2021-10-07 13:32:16', NULL);
INSERT INTO `file` VALUES ('99ada2d2977d408a928b0b8e1e7f2b78', 'file_ppt.png', 'c92f6026d0544585a9bae56723742369', 1, 'root/1/files/文件图标素材/file_ppt.png', 'image/png', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('a35e5f6734184db59d87ff19ba632072', 'WechatIMG64.jpeg', '625b8d0d9f1145849ecbcfff1b5c882a', 1, 'root/1/files/bbb/one/WechatIMG64.jpeg', 'image/jpeg', '2021-10-11 12:29:38', NULL);
INSERT INTO `file` VALUES ('ab3f5e8b8da146879e5feee8bbc7e607', 'file_excel.png', '82e65df4efb442e6a5cdbec60bac9a34', 1, 'root/1/files/c2/shaoduo/file_excel.png', 'image/png', '2021-10-07 13:31:31', NULL);
INSERT INTO `file` VALUES ('adcf543ecdb84924b93253957ed292ed', 'S10706-091756.jpg', '127ac05895ee42cb99e3934d98a6409a', 1, 'root/1/files/S10706-091756.jpg', 'image/jpeg', '2021-10-13 16:32:05', '176.00M');
INSERT INTO `file` VALUES ('b18bfa58412142cdb0ab1e58a4da3e1b', '2b8bc93d9bd931c27260da2a6a031269.jpeg', '127ac05895ee42cb99e3934d98a6409a', 1, 'root/1/files/2b8bc93d9bd931c27260da2a6a031269.jpeg', 'image/jpeg', '2021-09-30 10:25:15', NULL);
INSERT INTO `file` VALUES ('b8a525bfaa06418b8003f2f873abf77e', 'file_excel.png', 'c92f6026d0544585a9bae56723742369', 1, 'root/1/files/文件图标素材/file_excel.png', 'image/png', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('b9997d2d3c6244bfada5fc12abd8a205', '96.jpg', '2cbaa1bcd7e84e5f906e3aae449fde5a', 1, 'root/1/files/bbb/96.jpg', 'image/jpeg', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('c4208a1ea3734a4bade932d7bfc78565', 'aaa', '127ac05895ee42cb99e3934d98a6409a', 1, 'root/1/files/aaa/', 'FOLDER', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('c428dac67dc74675acf4fe20ae0b2745', 'bg3.jpg', '2cbaa1bcd7e84e5f906e3aae449fde5a', 1, 'root/1/files/bbb/bg3.jpg', 'image/jpeg', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('c80e4987e6ca4d69b6b7b03f2afa3a87', '综合素质评价.png', '127ac05895ee42cb99e3934d98a6409a', 1, 'root/1/files/综合素质评价.png', 'image/png', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('c92f6026d0544585a9bae56723742369', '文件图标素材', '127ac05895ee42cb99e3934d98a6409a', 1, 'root/1/files/文件图标素材/', 'FOLDER', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('cc1aa73b4100469c9a01c02d33d67f5e', 'file_jpg.png', 'c92f6026d0544585a9bae56723742369', 1, 'root/1/files/文件图标素材/file_jpg.png', 'image/png', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('d163cbf5cb9146ccbc741aec4b159b52', '李晓飞.png', '625b8d0d9f1145849ecbcfff1b5c882a', 1, 'root/1/files/bbb/one/李晓飞.png', 'image/png', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('d2c9c028c79f4dec8c759590f473b291', '综合素质评价.png', '127ac05895ee42cb99e3934d98a6409a', 1, 'root/1/files/综合素质评价.png', 'image/png', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('d51ff8fb25e349ab9ba6a44f4dab2736', 'file_word.png', '82e65df4efb442e6a5cdbec60bac9a34', 1, 'root/1/files/c2/shaoduo/file_word.png', 'image/png', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('d7936fd979524163a7c88fc071a076e0', 'file_excel.png', 'c92f6026d0544585a9bae56723742369', 1, 'root/1/files/文件图标素材/file_excel.png', 'image/png', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('db64ba60257e4c6fbfc6df77fee300bc', 'WechatIMG64.jpeg', '767e7e9fe3e24f17a35fc626fe5b9eba', 12, 'root/12/files/aaa/WechatIMG64.jpeg', 'image/jpeg', '2021-10-10 17:53:09', NULL);
INSERT INTO `file` VALUES ('ddf3f4c7e26b4228b321d7a70ffbd3d6', 'file_unknow.png', 'c92f6026d0544585a9bae56723742369', 1, 'root/1/files/文件图标素材/file_unknow.png', 'image/png', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('e070ed9868af42619fef8ce64636ca76', 'file_code.png', '82e65df4efb442e6a5cdbec60bac9a34', 1, 'root/1/files/c2/shaoduo/file_code.png', 'image/png', '2021-10-07 13:32:15', NULL);
INSERT INTO `file` VALUES ('e213911af8cb417bb81a0e9279db4f89', 'file_png.png', 'c92f6026d0544585a9bae56723742369', 1, 'root/1/files/文件图标素材/file_png.png', 'image/png', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('e70bab01761a4e6b8b780d72897f4046', '/', NULL, 12, 'root/12/files/', 'FOLDER', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('eb3ee79a138d43a7acfbc78a99e20d5d', 'file_unknow.png', '82e65df4efb442e6a5cdbec60bac9a34', 1, 'root/1/files/c2/shaoduo/file_unknow.png', 'image/png', '2021-10-07 13:32:17', NULL);
INSERT INTO `file` VALUES ('ef72466d3e0e4192ba9c6a060f81104b', '01b2e85722024a32f875a3996ba9be.jpg@1280w_1l_2o_100sh.JPG', '1fee18f1a94e4774b2cc2c7878cbf368', 1, 'root/1/files/c2/01b2e85722024a32f875a3996ba9be.jpg@1280w_1l_2o_100sh.JPG', 'image/jpeg', '2021-10-07 22:21:59', NULL);
INSERT INTO `file` VALUES ('f0262cd997e74bea8051bc5a00192dc2', '综合素质评价.png', '127ac05895ee42cb99e3934d98a6409a', 1, 'root/1/files/综合素质评价.png', 'image/png', '2021-09-29 17:26:56', NULL);
INSERT INTO `file` VALUES ('fa2d10b88ba746fea25255007979109a', '搜索.png', 'c92f6026d0544585a9bae56723742369', 1, 'root/1/files/文件图标素材/搜索.png', 'image/png', '2021-10-07 22:21:59', NULL);
COMMIT;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` varchar(32) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of product
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for trade
-- ----------------------------
DROP TABLE IF EXISTS `trade`;
CREATE TABLE `trade` (
  `id` varchar(32) NOT NULL,
  `amount` varchar(20) DEFAULT NULL COMMENT '交易量',
  `after_amount` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '交易后余额',
  `time` varchar(20) DEFAULT NULL COMMENT '交易时间',
  `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '交易用户',
  `mode` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '“支付宝”，“微信”，“其他”',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '“充值”和“消费”两类 ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of trade
-- ----------------------------
BEGIN;
INSERT INTO `trade` VALUES ('111', '20.15', '20.15', '2021-08-03 10:30:00', '1', '支付宝', '充值');
INSERT INTO `trade` VALUES ('112', '15', '5.15', '2021-08-03 10:31:00', '1', '', '消费');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户主键',
  `name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(30) DEFAULT NULL COMMENT '密码',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `mail` varchar(200) DEFAULT NULL COMMENT '邮箱',
  `vip` int DEFAULT NULL COMMENT 'vip',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `balance` varchar(10) DEFAULT NULL COMMENT '余额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, 'shaoduo', 'shaoduosd', '18722688087', '382300501@qq.com', 0, NULL, NULL);
INSERT INTO `user` VALUES (2, '18722688088', 'shaoduosd', '18722688088', NULL, 0, NULL, NULL);
INSERT INTO `user` VALUES (8, '18722688089', 'shaoduosd', '18722688089', NULL, 0, '2021-09-09 00:16:312', '0');
INSERT INTO `user` VALUES (9, '18722688090', 'shaoduosd', '18722688090', NULL, 0, '2021-09-09 00:20:935', '0');
INSERT INTO `user` VALUES (10, '18722688091', 'shaoduosd', '18722688091', NULL, 0, '2021-09-09 00:26:327', '0');
INSERT INTO `user` VALUES (11, '18722688092', 'shaduosd', '18722688092', NULL, 0, '2021-09-09 13:51:754', '0');
INSERT INTO `user` VALUES (12, '15620572755', 'shaoduosd', '15620572755', NULL, 0, '2021-10-10 16:38:65 ', '0');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
