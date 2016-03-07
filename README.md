BOX项目结构介绍
===================
----------
#package introduce
1.des：3des算法，加密用的<br>
2.device：无用<br>
3.html：测试html调用摄像头问题，目前还未实现<br>
4.md5：md5<br>
5.photo：仿照微信上传图片的做法<br>
6.rsa:rsa加密用的<br>
7.util:常用工具类<br>
8.phototwo:拍身份证照截图<br>
9.AIDL<br>
10.不同渠道的打包方法<br>
#assets


#so


#jar

#module
1.cameracropper:自定义拍照界面，实现拍照完可以自定义剪切<br>
用法：<br>
/*
*跳转方法
*/
 private void intentPhotoAct(){
        intentPhoto=new Intent(mContext,TakePhoteActivity.class);
        startActivityForResult(intentPhoto,requestCodePhoto);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        MyLogger.aLog().i("requestCode=" + requestCode);
        MyLogger.aLog().i("resultCode=" + resultCode);
        if(resultCode==RESULT_OK){

            switch (requestCode) {
                case requestCodePhoto://拍照成功
                   selectPhotoPahtResult(data.getData(),data.getStringExtra("path"));
                    break;

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

   private void selectPhotoPahtResult(Uri uri,String filepath){
       switch (index) {
           case R.id.iv_acc_err1://
               fileSFZZMPath = filepath;
               ivewSFZZM.setImageURI(uri);
               ivewSFZZM_pz.setImageResource(R.mipmap.photo2);
               break;


       }
   }
