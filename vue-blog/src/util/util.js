/**
 * 将"2018-05-19T08:04:52.000+0000"这种格式的时间转化为正常格式
 * @param time
 */
import DATA from '../admin/data'

export function timeFormatYMDHMS(time) {
    let d = new Date(time);

    let year = d.getFullYear();       //年
    let month = d.getMonth() + 1;     //月
    let day = d.getDate();            //日

    let hh = d.getHours();            //时
    let mm = d.getMinutes();          //分
    let ss = d.getSeconds();           //秒

    let clock = year + "-";

    if (month < 10)
        clock += "0";

    clock += month + "-";

    if (day < 10)
        clock += "0";

    clock += day + " ";

    if (hh < 10)
        clock += "0";

    clock += hh + ":";
    if (mm < 10) clock += '0';
    clock += mm + ":";

    if (ss < 10) clock += '0';
    clock += ss;
    return clock;
}

export function timeFormatYMD(time) {
    let d = new Date(time);

    let year = d.getFullYear();       //年
    let month = d.getMonth() + 1;     //月
    let day = d.getDate();            //日

    let clock = year + "-";

    if (month < 10)
        clock += "0";

    clock += month + "-";

    if (day < 10)
        clock += "0";

    clock += day;

    return clock;
}

export function transformEditorPost(object) {
    let res = DATA.postEditorData;
    res.id = object.id;
    res.title = object.title;
    res.content = object.content;
    res.description = object.description;
    res["type.id"] = object.type.id;
    res.tagIds = object.tagIds;
    res.flag = object.flag;
    res.firstPicture = object.firstPicture;
    res.recommend = object.recommend;
    res.shareStatement = object.shareStatement;
    res.appreciation = object.appreciation;
    res.commentabled = object.commentabled;
    res.published = object.published;
    return res;
}