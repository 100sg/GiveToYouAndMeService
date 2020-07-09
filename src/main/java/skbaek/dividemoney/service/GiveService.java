package skbaek.dividemoney.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skbaek.dividemoney.common.ExceptionString;
import skbaek.dividemoney.dto.MoneyGiveRequestDto;
import skbaek.dividemoney.dto.MoneyGiveResponseDto;
import skbaek.dividemoney.entity.give.MoneyGive;
import skbaek.dividemoney.entity.receive.MoneyReceive;
import skbaek.dividemoney.entity.give.MoneyGiveRepository;
import skbaek.dividemoney.entity.receive.MoneyReceiveRepository;
import skbaek.dividemoney.util.DivideUtil;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class GiveService {

    private final MoneyGiveRepository moneyGiveRepository;

    @Transactional
    public MoneyGiveResponseDto save(MoneyGiveRequestDto requestDto) {

        if( requestDto.getGiveMoney() < requestDto.getRecieveMens() ) throw new IllegalArgumentException(ExceptionString.LACK_OF_MONEY.getMsg());

        MoneyGive moneyGive = requestDto.toEntity();

        DivideUtil.devideListSet(DivideUtil.divideMoney(requestDto), moneyGive);

        return new MoneyGiveResponseDto(moneyGiveRepository.save(moneyGive));
    }

}
