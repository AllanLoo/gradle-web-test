import com.isp.common.utils.IdGen;
import com.isp.security.shiro.SystemService;
import junit.framework.TestCase;

/**
 * Created by Administrator on 2015/8/27.
 */
public class CaseTest extends TestCase{

    public void testEntryptPassword(){
        String pwd = "123456";
        String entryptPwd = SystemService.entryptPassword(pwd);
        System.out.println(entryptPwd);
    }

    public void testUUID(){
        String uid = IdGen.uuid();
        System.out.println(uid);
    }
}
