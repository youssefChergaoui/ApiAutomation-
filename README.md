openapi: 3.0.1
info:
  title: Messaging API
  description: Send text, email and push notifications
  contact:
    name: Digital Factory SGABS
    url: http://noop
    email: noreply@socgen.com
  version: 2.0.26
servers:
  - url: https://pvip-dc1-cbg.dns20.socgen/messaging/2
    description: Generated server url
tags:
  - name: Messaging
    description: 'All things messaging: email, sms, notifications'
paths:
  /sms:
    post:
      tags:
        - Messaging
      summary: Send a single text message
      operationId: sendTextMessage
      parameters:
        - $ref: '#/components/parameters/X-Application'
        - $ref: '#/components/parameters/X-TenantId'
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/SendTextMessageInput'
        required: true
      responses:
        '200':
          description: OK
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/MessageRequest'
  /email:
    post:
      tags:
        - Messaging
      summary: Send an email
      operationId: sendEmailMessage
      parameters:
        - $ref: '#/components/parameters/X-Application'
        - $ref: '#/components/parameters/X-TenantId'
      requestBody:
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/SendEmailMessageInput'
        required: true
      responses:
        '200':
          description: OK
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/MessageRequest'
  /message/{id}:
    get:
      tags:
        - Messaging
      summary: get message status
      operationId: checkMessage
      parameters:
        - $ref: '#/components/parameters/X-Application'
        - $ref: '#/components/parameters/X-TenantId'
        - name: id
          in: path
          required: true
          schema:
            type: string
      responses:
        '200':
          description: OK
          content:
            application/json:
              schema:
                $ref: '#/components/schemas/MessageRequest'
components:
  schemas:
    SendTextMessageInput:
      required:
        - to
      type: object
      properties:
        from:
          type: string
          description: Sender of the message. If left blank, default sender will used if configured
        to:
          type: string
          description: Phone number of the recipient of this message
        message:
          type: string
          description: Content of the message to send. If it is empty the Application will load template from database using application name, operation name and language
        operation:
          type: string
          description: Operation name, utilised to get template from database
        language:
          type: string
          description: Language of template content
        variables:
          type: object
          additionalProperties:
            type: object
            description: Message template arguments
          description: Message template arguments
        sendAt:
          type: string
          description: Schedule the message to be sent at a specific date and time
          format: date-time
    MessageRequest:
      type: object
      properties:
        id:
          type: string
        application:
          type: string
        status:
          type: string
          enum:
            - UNKNOWN
            - DELIVERED
            - EXPIRED
            - REJECTED
            - PENDING
            - UNDELIVERABLE
        type:
          type: string
          enum:
            - SMS
            - EMAIL
            - PUSH
        createdAt:
          type: string
          format: date-time
    SendEmailMessageInput:
      required:
        - to
      type: object
      properties:
        from:
          type: string
          description: Sender of the email. If left blank, default sender will used if configured
        to:
          type: array
          description: Emails of the recipients of this message
          items:
            type: string
            description: Emails of the recipients of this message
        cc:
          type: array
          description: Carbon copy, to send copies of an email to other emails.
          items:
            type: string
            description: Carbon copy, to send copies of an email to other emails.
        bcc:
          type: array
          description: Blind carbon copy, if recipients will not be able to see that someone else has been sent a copy of the email.
          items:
            type: string
            description: Blind carbon copy, if recipients will not be able to see that someone else has been sent a copy of the email.
        subject:
          type: string
          description: Subject of email
        content:
          type: string
          description: Content of email
        attachmentUrls:
          type: array
          description: An email attachment urls, One or more files can be attached to any email message, and be sent along with it to the recipients
          items:
            type: string
            description: An email attachment urls, One or more files can be attached to any email message, and be sent along with it to the recipients
        format:
          type: string
          description: Format Type of email
          example: HTML
          enum:
            - HTML
            - TEXT
        operation:
          type: string
          description: Operation name, utilised to get template from database
        language:
          type: string
          description: Language of template content
        subjectVariables:
          type: object
          additionalProperties:
            type: object
            description: Subject template variables
          description: Subject template variables
        contentVariables:
          type: object
          additionalProperties:
            type: object
            description: Content template variables
          description: Content template variables
        sendAt:
          type: string
          description: Schedule the message to be sent at a specific date and time
          format: date-time
  parameters:
    X-Application:
      name: X-Application
      in: header
      description: |
        Identifiant de l'application consommatrive de l'API.<br />
        Certains paramétrages peuvent être spécifiques à une application, il est donc important de fournir cette information de façon explicite
        ou depuis un JWT d'authentification
      required: false
      schema:
        type: string
    X-TenantId:
      name: X-TenantId
      in: header
      description: |
        This service is a multi-tenant tenant service. Sending a tenantId is required. The correct values are
        the country-code (ISO2) of Société Générale entities in AFS.<br />
        - **BF** Burkina (SGBF)
        - **BJ** Benin (SGB)
        - **CG** Congo (SGC)
        - **CI** Côte d'Ivoire (SGBCI)
        - **CM** Cameroon (SGCAM)
        - **GH** Ghana (SGGH)
        - **GN** Guinee Conakry (SGBG)
        - **MG** Madagascar (BFVSG)
        - **MR** Mauritanie (SGM)
        - **SN** Senegal (SGBS)
        - **TD** Tchad (SGT)
      required: false
      schema:
        type: string
        enum:
          - BF
          - BJ
          - CG
          - CI
          - CM
          - GH
          - GN
          - MG
          - MR
          - SN
          - TD
  securitySchemes:
    OAUTH2:
      type: oauth2
      description: OAuth2 OpenID Connect
      flows:
        password:
          tokenUrl: http://localhost:9000/token
          scopes: {}
