import DATA from '../admin/data'

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

export function clearEditorForm() {
    DATA.editorForm.id = '';
    DATA.editorForm.title = '';
    DATA.editorForm.content = '';
    DATA.editorForm.description = '';
    DATA.editorForm.type.id = '';
    DATA.editorForm.tagIds = [];
    DATA.editorForm.flag = '';
    DATA.editorForm.firstPicture = '';
    DATA.editorForm.recommend = true;
    DATA.editorForm.shareStatement = true;
    DATA.editorForm.appreciation = true;
    DATA.editorForm.commentabled = true;
    DATA.editorForm.published = '';
}
