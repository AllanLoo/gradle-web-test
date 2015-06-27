package com.isp.security.shiro.session;

import com.isp.common.utils.IdGen;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;

/**
 * Created by allan on 15-6-20.
 */
public class SessionIdGen implements SessionIdGenerator {
    @Override
    public Serializable generateId(Session session) {
        return IdGen.uuid();
    }
}
