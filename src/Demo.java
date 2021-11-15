import com.*;
import com.encrypty.*;
import com.table.bloodTable;
import com.table.fatherTable;

import java.io.IOException;
import java.util.Vector;

/**
 * @ClassName Demo
 * @Description CIPE??????
 * @Date 2020/10/12 21:21
 * @Version 1.0
 */
public class Demo {

    /**
     * ��ȡ��ȫ��������
     *
     * @param data ����
     * @param sk   ��Կ
     * @return
     */
    public static splitedMatrix getSecureDataIndex(double data, SecretKey sk) {
        splitedMatrix secureDataIndex = null;

        // ������������
        IndexEncrypty indexEncrypty = new IndexEncrypty();
        double[] index = indexEncrypty.BuildIndexVector(data, sk);

        // ��������
        splitedMatrix splitedMatrix = indexEncrypty.splitIndexVector(index, sk);
        secureDataIndex = indexEncrypty.EncIndexVector(splitedMatrix, sk);

        return secureDataIndex;
    }

    /**
     * ��ȡ��ȫ��ѯ����
     *
     * @param data ��ѯ�߽�ֵ
     * @param sk
     * @return ��Կ
     */
    public static splitedMatrix getSecureQueryIndex(double data, SecretKey sk) {
        splitedMatrix secureQueryIndex = null;

        // ������������
        QueryEncrypty queryEncrypty = new QueryEncrypty();
        double[] index = queryEncrypty.BuildQueryVector(data, sk);

        // ��������
        splitedMatrix splitedMatrix = queryEncrypty.splitQueryVector(index, sk);
        secureQueryIndex = queryEncrypty.EncQueryVector(splitedMatrix, sk);

        return secureQueryIndex;
    }


    public static void main(String[] args) throws IOException {
    //    secret.getSecureDataIndex(2.0);
      //  secret.getSecureDataIndex(2.0);
      //  System.out.println(secret.getSecureDataIndex(2.0));
       // System.out.println(secret.getSecureDataIndex(2.0));

      bloodTable bt=new bloodTable();
     //String s=AES.encrypt(2.0);
    //  System.out.println(AES.decrypt(s));
        Vector<fatherTable> ftV= csv.csvRead("C:\\Users\\32809\\Desktop\\test.csv","ѪҺ��");
      //  bloodTable bt=new bloodTable();
        for(int i=0;i<ftV.size();++i){
            bt= (bloodTable) secret.Secret(ftV.elementAt(i));
       //     System.out.println(bt.num);
            DBH.Add("ѪҺ��",bt.m_patientID,bt.m_date,bt.AESString,bt.dataIndex);
        }
      bt.m_type="ѪҺ��";
      bt.m_name="zzq";
        System.out.println(DBH.search(jsonHelper.TestJson()));
    //  System.out.println(DBH.search(jsonHelper.JsonToJson(jsonHelper.TestJson2())));
    //  System.out.println(jsonHelper.TestJson2());
    //    System.out.println(jsonHelper.BtalbeToJson(bt));
        //  com.DBH.search();

     //   System.out.println(ft.elementAt(0).m_name);
      //  System.out.println(jsonHelper.talbeToJson(ft.elementAt(1)));
        //��ȡ��Կ
   //     SecretKey sk = Gen.GenKey(64);
     //   System.out.println(sk);
   //     double data = -113.0;
    //    double queryLeft = 2.0;
      //  double queryRight = 4.0;
       // String str= AES.encrypt(data);
    //    System.out.println(str+" "+com.AES.decrypt(str));
        // ��������
      //  splitedMatrix dataIndex1 = getSecureDataIndex(data, sk);

   //     ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("test"));

       // com.DBH.Add("bloodTalbe",str,dataIndex1);
    //    DBH.search("bloodTable",sk);


     //   splitedMatrix queryIndexLeft = getSecureQueryIndex(queryLeft, sk);
       // splitedMatrix queryIndexRight = getSecureQueryIndex(queryRight, sk);

        // ��ѯ ���Ϊ0�����㷶Χ��ѯ����
        // �����䷶Χ��ѯ
     //   System.out.println(""+dataIndex1.toString());
   //     System.out.println(new Query().Search(queryIndexLeft, queryIndexRight, dataIndex1));


    }



}
