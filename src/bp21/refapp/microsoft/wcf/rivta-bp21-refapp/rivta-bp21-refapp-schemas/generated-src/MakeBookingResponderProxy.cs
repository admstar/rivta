﻿//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:2.0.50727.3620
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------



/// <remarks/>
[System.CodeDom.Compiler.GeneratedCodeAttribute("svcutil", "3.0.4506.2152")]
[System.SerializableAttribute()]
[System.Diagnostics.DebuggerStepThroughAttribute()]
[System.ComponentModel.DesignerCategoryAttribute("code")]
[System.Xml.Serialization.XmlTypeAttribute(Namespace="urn:riv:crm:scheduling:MakeBookingResponder:1")]
public partial class MakeBookingType
{
    
    private string healthcare_facility_medField;
    
    private TimeslotType requestedTimeslotField;
    
    private SubjectOfCareType subject_of_care_infoField;
    
    private string notificationField;
    
    private System.Xml.XmlElement[] anyField;
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Order=0)]
    public string healthcare_facility_med
    {
        get
        {
            return this.healthcare_facility_medField;
        }
        set
        {
            this.healthcare_facility_medField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Order=1)]
    public TimeslotType requestedTimeslot
    {
        get
        {
            return this.requestedTimeslotField;
        }
        set
        {
            this.requestedTimeslotField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Order=2)]
    public SubjectOfCareType subject_of_care_info
    {
        get
        {
            return this.subject_of_care_infoField;
        }
        set
        {
            this.subject_of_care_infoField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Order=3)]
    public string notification
    {
        get
        {
            return this.notificationField;
        }
        set
        {
            this.notificationField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlAnyElementAttribute(Order=4)]
    public System.Xml.XmlElement[] Any
    {
        get
        {
            return this.anyField;
        }
        set
        {
            this.anyField = value;
        }
    }
}

/// <remarks/>
[System.CodeDom.Compiler.GeneratedCodeAttribute("svcutil", "3.0.4506.2152")]
[System.SerializableAttribute()]
[System.Diagnostics.DebuggerStepThroughAttribute()]
[System.ComponentModel.DesignerCategoryAttribute("code")]
[System.Xml.Serialization.XmlTypeAttribute(Namespace="urn:riv:crm:scheduling:1")]
public partial class TimeslotType
{
    
    private string startTimeInclusiveField;
    
    private string endTimeExclusiveField;
    
    private string healthcare_facilityField;
    
    private string performerField;
    
    private string bookingIdField;
    
    private string subject_of_careField;
    
    private string purposeField;
    
    private string reasonField;
    
    private string resourceNameField;
    
    private string healthcare_facility_nameField;
    
    private string performerNameField;
    
    private string resourceIDField;
    
    private string timeTypeNameField;
    
    private string timeTypeIDField;
    
    private string careTypeNameField;
    
    private string careTypeIDField;
    
    private bool cancel_booking_allowedField;
    
    private bool cancel_booking_allowedFieldSpecified;
    
    private bool rebooking_allowedField;
    
    private bool rebooking_allowedFieldSpecified;
    
    private bool message_allowedField;
    
    private bool message_allowedFieldSpecified;
    
    private System.Xml.XmlElement[] anyField;
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Order=0)]
    public string startTimeInclusive
    {
        get
        {
            return this.startTimeInclusiveField;
        }
        set
        {
            this.startTimeInclusiveField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Order=1)]
    public string endTimeExclusive
    {
        get
        {
            return this.endTimeExclusiveField;
        }
        set
        {
            this.endTimeExclusiveField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Order=2)]
    public string healthcare_facility
    {
        get
        {
            return this.healthcare_facilityField;
        }
        set
        {
            this.healthcare_facilityField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Order=3)]
    public string performer
    {
        get
        {
            return this.performerField;
        }
        set
        {
            this.performerField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Order=4)]
    public string bookingId
    {
        get
        {
            return this.bookingIdField;
        }
        set
        {
            this.bookingIdField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Order=5)]
    public string subject_of_care
    {
        get
        {
            return this.subject_of_careField;
        }
        set
        {
            this.subject_of_careField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Order=6)]
    public string purpose
    {
        get
        {
            return this.purposeField;
        }
        set
        {
            this.purposeField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Order=7)]
    public string reason
    {
        get
        {
            return this.reasonField;
        }
        set
        {
            this.reasonField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Order=8)]
    public string resourceName
    {
        get
        {
            return this.resourceNameField;
        }
        set
        {
            this.resourceNameField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Order=9)]
    public string healthcare_facility_name
    {
        get
        {
            return this.healthcare_facility_nameField;
        }
        set
        {
            this.healthcare_facility_nameField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Order=10)]
    public string performerName
    {
        get
        {
            return this.performerNameField;
        }
        set
        {
            this.performerNameField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Order=11)]
    public string resourceID
    {
        get
        {
            return this.resourceIDField;
        }
        set
        {
            this.resourceIDField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Order=12)]
    public string timeTypeName
    {
        get
        {
            return this.timeTypeNameField;
        }
        set
        {
            this.timeTypeNameField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Order=13)]
    public string timeTypeID
    {
        get
        {
            return this.timeTypeIDField;
        }
        set
        {
            this.timeTypeIDField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Order=14)]
    public string careTypeName
    {
        get
        {
            return this.careTypeNameField;
        }
        set
        {
            this.careTypeNameField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Order=15)]
    public string careTypeID
    {
        get
        {
            return this.careTypeIDField;
        }
        set
        {
            this.careTypeIDField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Order=16)]
    public bool cancel_booking_allowed
    {
        get
        {
            return this.cancel_booking_allowedField;
        }
        set
        {
            this.cancel_booking_allowedField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public bool cancel_booking_allowedSpecified
    {
        get
        {
            return this.cancel_booking_allowedFieldSpecified;
        }
        set
        {
            this.cancel_booking_allowedFieldSpecified = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Order=17)]
    public bool rebooking_allowed
    {
        get
        {
            return this.rebooking_allowedField;
        }
        set
        {
            this.rebooking_allowedField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public bool rebooking_allowedSpecified
    {
        get
        {
            return this.rebooking_allowedFieldSpecified;
        }
        set
        {
            this.rebooking_allowedFieldSpecified = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Order=18)]
    public bool message_allowed
    {
        get
        {
            return this.message_allowedField;
        }
        set
        {
            this.message_allowedField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlIgnoreAttribute()]
    public bool message_allowedSpecified
    {
        get
        {
            return this.message_allowedFieldSpecified;
        }
        set
        {
            this.message_allowedFieldSpecified = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlAnyElementAttribute(Order=19)]
    public System.Xml.XmlElement[] Any
    {
        get
        {
            return this.anyField;
        }
        set
        {
            this.anyField = value;
        }
    }
}

/// <remarks/>
[System.CodeDom.Compiler.GeneratedCodeAttribute("svcutil", "3.0.4506.2152")]
[System.SerializableAttribute()]
[System.Diagnostics.DebuggerStepThroughAttribute()]
[System.ComponentModel.DesignerCategoryAttribute("code")]
[System.Xml.Serialization.XmlTypeAttribute(Namespace="urn:riv:crm:scheduling:1")]
public partial class SubjectOfCareType
{
    
    private string phoneField;
    
    private string emailField;
    
    private string addressField;
    
    private string coaddressField;
    
    private string firstNameField;
    
    private string middleNameField;
    
    private string lastNameField;
    
    private System.Xml.XmlElement[] anyField;
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Order=0)]
    public string phone
    {
        get
        {
            return this.phoneField;
        }
        set
        {
            this.phoneField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Order=1)]
    public string email
    {
        get
        {
            return this.emailField;
        }
        set
        {
            this.emailField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Order=2)]
    public string address
    {
        get
        {
            return this.addressField;
        }
        set
        {
            this.addressField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Order=3)]
    public string coaddress
    {
        get
        {
            return this.coaddressField;
        }
        set
        {
            this.coaddressField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Namespace="urn:riv:crm:scheduling:1.1", Order=4)]
    public string firstName
    {
        get
        {
            return this.firstNameField;
        }
        set
        {
            this.firstNameField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Namespace="urn:riv:crm:scheduling:1.1", Order=5)]
    public string middleName
    {
        get
        {
            return this.middleNameField;
        }
        set
        {
            this.middleNameField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlElementAttribute(Namespace="urn:riv:crm:scheduling:1.1", Order=6)]
    public string lastName
    {
        get
        {
            return this.lastNameField;
        }
        set
        {
            this.lastNameField = value;
        }
    }
    
    /// <remarks/>
    [System.Xml.Serialization.XmlAnyElementAttribute(Order=7)]
    public System.Xml.XmlElement[] Any
    {
        get
        {
            return this.anyField;
        }
        set
        {
            this.anyField = value;
        }
    }
}

/// <remarks/>
[System.CodeDom.Compiler.GeneratedCodeAttribute("svcutil", "3.0.4506.2152")]
[System.SerializableAttribute()]
[System.Xml.Serialization.XmlTypeAttribute(Namespace="urn:riv:crm:scheduling:1")]
public enum ResultCodeEnum
{
    
    /// <remarks/>
    OK,
    
    /// <remarks/>
    ERROR,
    
    /// <remarks/>
    INFO,
}
namespace rivta_bp21_refapp_schemas.wsdl
{
    
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "3.0.0.0")]
    [System.ServiceModel.ServiceContractAttribute(Namespace="urn:riv:crm:scheduling:MakeBooking:1:rivtabp21", ConfigurationName="rivta_bp21_refapp_schemas.wsdl.MakeBookingResponderInterface")]
    public interface MakeBookingResponderInterface
    {
        
        // CODEGEN: Generating message contract since the operation MakeBooking is neither RPC nor document wrapped.
        [System.ServiceModel.OperationContractAttribute(Action="urn:riv:crm:scheduling:MakeBookingResponder:1:MakeBooking", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute()]
        rivta_bp21_refapp_schemas.wsdl.MakeBookingResponse MakeBooking(rivta_bp21_refapp_schemas.wsdl.MakeBookingRequest request);
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "3.0.0.0")]
    [System.ServiceModel.MessageContractAttribute(IsWrapped=false)]
    public partial class MakeBookingRequest
    {
        
        [System.ServiceModel.MessageHeaderAttribute(Namespace="urn:riv:itintegration:registry:1")]
        public string LogicalAddress;
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="urn:riv:crm:scheduling:MakeBookingResponder:1", Order=0)]
        public MakeBookingType MakeBooking;
        
        public MakeBookingRequest()
        {
        }
        
        public MakeBookingRequest(string LogicalAddress, MakeBookingType MakeBooking)
        {
            this.LogicalAddress = LogicalAddress;
            this.MakeBooking = MakeBooking;
        }
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "3.0.0.0")]
    [System.ServiceModel.MessageContractAttribute(WrapperName="MakeBookingResponse", WrapperNamespace="urn:riv:crm:scheduling:MakeBookingResponder:1", IsWrapped=true)]
    public partial class MakeBookingResponse
    {
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="urn:riv:crm:scheduling:MakeBookingResponder:1", Order=0)]
        public string bookingId;
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="urn:riv:crm:scheduling:MakeBookingResponder:1", Order=1)]
        public ResultCodeEnum resultCode;
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="urn:riv:crm:scheduling:MakeBookingResponder:1", Order=2)]
        public string resultText;
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="", Order=3)]
        [System.Xml.Serialization.XmlAnyElementAttribute()]
        public System.Xml.XmlElement[] Any;
        
        public MakeBookingResponse()
        {
        }
        
        public MakeBookingResponse(string bookingId, ResultCodeEnum resultCode, string resultText, System.Xml.XmlElement[] Any)
        {
            this.bookingId = bookingId;
            this.resultCode = resultCode;
            this.resultText = resultText;
            this.Any = Any;
        }
    }
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "3.0.0.0")]
    public interface MakeBookingResponderInterfaceChannel : rivta_bp21_refapp_schemas.wsdl.MakeBookingResponderInterface, System.ServiceModel.IClientChannel
    {
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "3.0.0.0")]
    public partial class MakeBookingResponderInterfaceClient : System.ServiceModel.ClientBase<rivta_bp21_refapp_schemas.wsdl.MakeBookingResponderInterface>, rivta_bp21_refapp_schemas.wsdl.MakeBookingResponderInterface
    {
        
        public MakeBookingResponderInterfaceClient()
        {
        }
        
        public MakeBookingResponderInterfaceClient(string endpointConfigurationName) : 
                base(endpointConfigurationName)
        {
        }
        
        public MakeBookingResponderInterfaceClient(string endpointConfigurationName, string remoteAddress) : 
                base(endpointConfigurationName, remoteAddress)
        {
        }
        
        public MakeBookingResponderInterfaceClient(string endpointConfigurationName, System.ServiceModel.EndpointAddress remoteAddress) : 
                base(endpointConfigurationName, remoteAddress)
        {
        }
        
        public MakeBookingResponderInterfaceClient(System.ServiceModel.Channels.Binding binding, System.ServiceModel.EndpointAddress remoteAddress) : 
                base(binding, remoteAddress)
        {
        }
        
        [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Advanced)]
        rivta_bp21_refapp_schemas.wsdl.MakeBookingResponse rivta_bp21_refapp_schemas.wsdl.MakeBookingResponderInterface.MakeBooking(rivta_bp21_refapp_schemas.wsdl.MakeBookingRequest request)
        {
            return base.Channel.MakeBooking(request);
        }
        
        public string MakeBooking(string LogicalAddress, MakeBookingType MakeBooking1, out ResultCodeEnum resultCode, out string resultText, out System.Xml.XmlElement[] Any)
        {
            rivta_bp21_refapp_schemas.wsdl.MakeBookingRequest inValue = new rivta_bp21_refapp_schemas.wsdl.MakeBookingRequest();
            inValue.LogicalAddress = LogicalAddress;
            inValue.MakeBooking = MakeBooking1;
            rivta_bp21_refapp_schemas.wsdl.MakeBookingResponse retVal = ((rivta_bp21_refapp_schemas.wsdl.MakeBookingResponderInterface)(this)).MakeBooking(inValue);
            resultCode = retVal.resultCode;
            resultText = retVal.resultText;
            Any = retVal.Any;
            return retVal.bookingId;
        }
    }
}
