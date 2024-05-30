package uk.co.argon.common.filter;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.lang3.StringUtils;

import jakarta.annotation.Priority;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.ext.Provider;
import uk.co.argon.common.stream.LoggingInputStream;
import uk.co.argon.common.stream.RequestBufferOutputStream;
import uk.co.argon.common.util.ArgonConstants;

@Provider
@Priority(500)
public class ServiceSecurityAuditRequestFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext ctx) throws IOException {
		auditRequest(ctx);
	}
	
	private void auditRequest(ContainerRequestContext ctx) {
		if(!(StringUtils.equalsIgnoreCase(ctx.getMethod(), ArgonConstants.GET) || ctx.getEntityStream() == null)) {
			InputStream originalInputStream = ctx.getEntityStream();
			RequestBufferOutputStream rbos = new RequestBufferOutputStream();
			LoggingInputStream lis = new LoggingInputStream(originalInputStream, rbos);

			ctx.setEntityStream(lis);
		}
	}

}
