package heaven.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import heaven.model.parameter.UriParameter;
import heaven.parser.ParseException;

public class Heaven
{

    private String title;
    private String version;
    private String baseUri;
    private Map<String, UriParameter> uriParameters = new HashMap<String, UriParameter>();
    private Map<String, Trait> traits = new HashMap<String, Trait>();
    private ResourceMap resources = new ResourceMap();

    private static final List<String> VALID_KEYS = Arrays.asList(
            "title", "version", "baseUri", "uriParameters", "documentation", "traits");
    private static final List<String> REQUIRED_FIELDS = Arrays.asList(
            "title", "baseUri");
    private static final int TITLE_MAX_LENGTH = 48;

    public Heaven(Map<String, ?> descriptor)
    {
        validateRequiredFields(descriptor);
        checkInvalidFields(descriptor);

        title = (String) descriptor.get("title");
        if (title.length() > TITLE_MAX_LENGTH)
        {
            throw new ParseException("Title too long, maximum character length is " + TITLE_MAX_LENGTH);
        }
        version = (String) descriptor.get("version");
        baseUri = (String) descriptor.get("baseUri");
        try
        {
            new URL(baseUri);
        }
        catch (MalformedURLException e)
        {
            throw new ParseException("Malformed baseUri: " + baseUri);
        }
        if (descriptor.containsKey("uriParameters"))
        {
            populateUriParameters((Map<String, ?>) descriptor.get("uriParameters"));
        }

        resources.populate(descriptor, baseUri);
        List<Resource> collisions = resources.checkResourceCollision();
        if (!collisions.isEmpty())
        {
            StringBuilder sb = new StringBuilder("\n");
            for (Resource r : collisions)
            {
                sb.append(" -> ").append(r.getUri()).append("\n");
            }
            throw new ParseException("Duplicate resource paths: " + sb);
        }
    }

    private void populateUriParameters(Map<String, ?> descriptor)
    {
        for (String param : descriptor.keySet())
        {
            //TODO do proper parsing with 3rd party lib
            if (baseUri.indexOf("{" + param + "}") == -1)
            {
                throw new ParseException(String.format("Base URI (%s) does not define \"%s\" parameter",
                                                       baseUri, param));
            }
            uriParameters.put(param, new UriParameter((Map<String, ?>) descriptor.get(param)));
        }
    }

    private void validateRequiredFields(Map<String, ?> descriptor)
    {
        for (String key : REQUIRED_FIELDS)
        {
            if (!descriptor.containsKey(key))
            {
                throw new ParseException(key + " not defined");
            }
        }

        for (String key : descriptor.keySet())
        {
            if (key.startsWith("/"))
            {
                return;
            }
        }
        throw new ParseException("at least one resource must be defined");
    }

    private void checkInvalidFields(Map<String, ?> descriptor)
    {
        List<String> invalidKeys = new ArrayList<String>();
        for (String key : descriptor.keySet())
        {
            if (!key.startsWith("/") && !VALID_KEYS.contains(key))
            {
                invalidKeys.add(key);
            }
        }
        if (!invalidKeys.isEmpty())
        {
            throw new ParseException("invalid top level keys: " + invalidKeys);
        }
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getBaseUri()
    {
        return baseUri;
    }

    public void setBaseUri(String baseUri)
    {
        this.baseUri = baseUri;
    }

    public Map<String, Trait> getTraits()
    {
        return traits;
    }

    public void addTrait(Trait trait)
    {
        traits.put(trait.getKey(), trait);
    }

    public ResourceMap getResources()
    {
        return resources;
    }

    public Map<String, UriParameter> getUriParameters()
    {
        return uriParameters;
    }

}
