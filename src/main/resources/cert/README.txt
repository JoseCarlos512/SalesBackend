CERTIFICADO DIGITAL SUNAT
=========================

Coloca aquí tu certificado digital en formato PKCS12 (.pfx / .p12).

Para el ambiente BETA de SUNAT puedes usar el certificado demo:
  - Descárgalo de: https://www.sunat.gob.pe/ol-ti-itcpgem-beta/billService
  - O usa cualquier certificado autofirmado para pruebas.

Configura las propiedades en application.properties (o variables de entorno):
  sunat.certificate.path=classpath:cert/TU_CERTIFICADO.pfx
  sunat.certificate.password=TU_PASSWORD
  sunat.certificate.alias=TU_ALIAS   (opcional, se detecta automáticamente)

PRODUCCIÓN: Obtén tu certificado de una Entidad de Certificación autorizada
por SUNAT (DigiCert, Certisur, etc.) o directamente de SUNAT.
