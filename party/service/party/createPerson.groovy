// Validate required parameters
if (!partyId || !firstName || !lastName) {
    ec.message.addError("partyId, firstName and lastName are required.")
    return
}

// Check if Party exists
def party = ec.entity.find("party.Party").condition("partyId", partyId).one()

if (!party) {
    ec.message.addError("Party with ID ${partyId} does not exist.")
    return
}

// Check if Person already exists
def person = ec.entity.find("party.Person").condition("partyId", partyId).one()
if (person) {
    ec.message.addError("Person with ID ${partyId} already exists.")
    return
}

// Create Person record
person = ec.entity.makeValue("party.Person")
person.setFields(context, true, null, null)

person.partyId = partyId
person.firstName = firstName
person.lastName = lastName

if (context.dateOfBirth) {
    person.date = context.dateOfBirth
}

person.create()

responseMessage = "Person ${firstName} ${lastName} created successfully!"