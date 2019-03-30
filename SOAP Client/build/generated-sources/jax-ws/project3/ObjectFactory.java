
package project3;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the project3 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ValidateChain_QNAME = new QName("http://project3/", "validateChain");
    private final static QName _ValidateChainResponse_QNAME = new QName("http://project3/", "validateChainResponse");
    private final static QName _ViewChain_QNAME = new QName("http://project3/", "viewChain");
    private final static QName _AddBlock_QNAME = new QName("http://project3/", "addBlock");
    private final static QName _ViewChainResponse_QNAME = new QName("http://project3/", "viewChainResponse");
    private final static QName _AddBlockResponse_QNAME = new QName("http://project3/", "addBlockResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: project3
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ValidateChain }
     * 
     */
    public ValidateChain createValidateChain() {
        return new ValidateChain();
    }

    /**
     * Create an instance of {@link ValidateChainResponse }
     * 
     */
    public ValidateChainResponse createValidateChainResponse() {
        return new ValidateChainResponse();
    }

    /**
     * Create an instance of {@link ViewChain }
     * 
     */
    public ViewChain createViewChain() {
        return new ViewChain();
    }

    /**
     * Create an instance of {@link AddBlock }
     * 
     */
    public AddBlock createAddBlock() {
        return new AddBlock();
    }

    /**
     * Create an instance of {@link ViewChainResponse }
     * 
     */
    public ViewChainResponse createViewChainResponse() {
        return new ViewChainResponse();
    }

    /**
     * Create an instance of {@link AddBlockResponse }
     * 
     */
    public AddBlockResponse createAddBlockResponse() {
        return new AddBlockResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateChain }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://project3/", name = "validateChain")
    public JAXBElement<ValidateChain> createValidateChain(ValidateChain value) {
        return new JAXBElement<ValidateChain>(_ValidateChain_QNAME, ValidateChain.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidateChainResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://project3/", name = "validateChainResponse")
    public JAXBElement<ValidateChainResponse> createValidateChainResponse(ValidateChainResponse value) {
        return new JAXBElement<ValidateChainResponse>(_ValidateChainResponse_QNAME, ValidateChainResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewChain }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://project3/", name = "viewChain")
    public JAXBElement<ViewChain> createViewChain(ViewChain value) {
        return new JAXBElement<ViewChain>(_ViewChain_QNAME, ViewChain.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddBlock }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://project3/", name = "addBlock")
    public JAXBElement<AddBlock> createAddBlock(AddBlock value) {
        return new JAXBElement<AddBlock>(_AddBlock_QNAME, AddBlock.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ViewChainResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://project3/", name = "viewChainResponse")
    public JAXBElement<ViewChainResponse> createViewChainResponse(ViewChainResponse value) {
        return new JAXBElement<ViewChainResponse>(_ViewChainResponse_QNAME, ViewChainResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddBlockResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://project3/", name = "addBlockResponse")
    public JAXBElement<AddBlockResponse> createAddBlockResponse(AddBlockResponse value) {
        return new JAXBElement<AddBlockResponse>(_AddBlockResponse_QNAME, AddBlockResponse.class, null, value);
    }

}
