package br.com.unipds;

import jakarta.enterprise.util.AnnotationLiteral;

public class FormatEbookFilter extends AnnotationLiteral<FormatEbookQualifier> implements FormatEbookQualifier{

    private final FormatEbookEnum value;

    private FormatEbookFilter(FormatEbookEnum value) {
        this.value = value;
    }

    @Override
    public FormatEbookEnum value() {
        return value;
    }

    public static FormatEbookFilter of(FormatEbookEnum format){
        return new FormatEbookFilter(format);
    }
}
