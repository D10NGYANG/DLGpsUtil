package com.d10ng.gpslib.bean

data class Regeocode(
    // 结构化地址信息包括：省份＋城市＋区县＋城镇＋乡村＋街道＋门牌号码
    // 如果坐标点处于海域范围内，则结构化地址信息为：省份＋城市＋区县＋海域信息
    var formattedAddress: String = "",

    // 国家
    var country: String = "",

    // 坐标点所在省名称
    var province: String = "",

    // 坐标点所在城市名称
    var city: String = "",

    // 城市编码
    var citycode: String = "",

    // 坐标点所在区
    var district: String = "",

    // 行政区编码
    var adcode: String = "",

    // 坐标点所在乡镇/街道（此街道为社区街道，不是道路信息）
    var township: String = "",

    // 乡镇街道编码
    var towncode: String = "",

    // 社区信息
    var neighborhood: String = "",

    // 建筑名称
    var building: String = ""
)