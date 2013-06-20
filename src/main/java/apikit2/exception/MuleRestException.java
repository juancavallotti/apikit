package apikit2.exception;

import org.mule.api.MuleException;
import org.mule.config.i18n.Message;

public class MuleRestException extends MuleException
{

    public MuleRestException(Message message)
    {
        super(message);
    }

    public MuleRestException(Message message, Throwable cause)
    {
        super(message, cause);
    }

    public MuleRestException(Throwable cause)
    {
        super(cause);
    }

    public MuleRestException()
    {
    }
}