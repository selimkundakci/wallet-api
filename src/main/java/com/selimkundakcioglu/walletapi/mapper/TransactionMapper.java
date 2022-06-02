package com.selimkundakcioglu.walletapi.mapper;

import com.selimkundakcioglu.walletapi.model.dto.TransactionDto;
import com.selimkundakcioglu.walletapi.model.entity.Transaction;
import org.mapstruct.Mapper;

@Mapper
public interface TransactionMapper {

    TransactionDto toTransactionDto(Transaction transaction);
}
