package com.example.bank.rest.controller.admin;

import com.example.bank.rest.dto.cards.*;
import com.example.bank.rest.dto.users.UserCreateDto;
import com.example.bank.rest.dto.users.UserDto;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@OpenAPIDefinition(info = @Info(title = "API ля управления пользователями и картами", version = "1.0"))
@Tag(name = "Основное API", description = "API для управления пользователями и их картами")
public interface AdminController {
    /**
     * GET /api/admin/cards : Получить все карты (ADMIN)
     *
     * @param page  (optional, default to 0)
     * @param size  (optional, default to 20)
     * @return Список всех карт (status code 200)
     */
    @Operation(
        operationId = "apiAdminCardsGet",
        summary = "Получить все карты (ADMIN)",
        tags = { "Карты (ADMIN)" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Список всех карт", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = CardDto.class))
            })
        },
        security = {
            @SecurityRequirement(name = "bearerAuth")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/admin/cards",
        produces = { "application/json" }
    )
    ResponseEntity<Page<CardDto>> getCardsAdmin(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size);

    /**
     * POST /cards : Создать новую карту (ADMIN)
     *
     * @param cardCreateRequest  (required)
     * @return Карта создана (status code 201)
     *         or Доступ запрещён (не ADMIN) (status code 403)
     */
    @Operation(
        operationId = "apiAdminCardsPost",
        summary = "Создать новую карту (ADMIN)",
        tags = { "Карты (ADMIN)" },
        responses = {
            @ApiResponse(responseCode = "201", description = "Карта создана", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = CardDto.class))
            }),
            @ApiResponse(responseCode = "403", description = "Доступ запрещён (не ADMIN)")
        },
        security = {
            @SecurityRequirement(name = "bearerAuth")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/api/users/{id}/cards",
        produces = { "application/json" },
        consumes = { "application/json" }
    )

    ResponseEntity<CardDto> apiAdminCardsPost(
        @PathVariable Integer id,
        @Parameter(name = "CardCreateRequest", description = "", required = true)
        @Valid
        @RequestBody CardCreateDto cardCreateRequest
    ) throws Exception;

    /**
     * GET /api/admin/users : Получить роли всех пользователей (ADMIN)
     *
     * @param page  (optional, default to 0)
     * @param size  (optional, default to 20)
     * @return Список всех пользователей (status code 200)
     */
    @Operation(
        operationId = "apiAdminUsersGet",
        summary = "Получить роли всех пользователей (ADMIN)",
        tags = { "Пользователи (ADMIN)" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Список всех пользователей", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
            })
        },
        security = {
            @SecurityRequirement(name = "bearerAuth")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/admin/users",
        produces = { "application/json" }
    )
    ResponseEntity<Page<UserDto>> getUsersAdmin(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size);

    /**
     * DELETE /api/admin/cards/{id} : Удалить карту (ADMIN)
     *
     * @param id  (required)
     * @return Карта удалена (status code 204)
     *         or Карта не найдена (status code 404)
     */
    @Operation(
        operationId = "apiAdminCardsIdDelete",
        summary = "Удалить карту (ADMIN)",
        tags = { "Карты (ADMIN)" },
        responses = {
            @ApiResponse(responseCode = "204", description = "Карта удалена"),
            @ApiResponse(responseCode = "404", description = "Карта не найдена")
        },
        security = {
            @SecurityRequirement(name = "bearerAuth")
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/api/admin/cards/{id}"
    )

    ResponseEntity<Void> apiAdminCardsIdDelete(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH)
        @PathVariable("id")
        Integer id
    );

    /**
     * PATCH /api/admin/cards/{id}/status : Изменить статус карты (активировать/блокировать) (ADMIN)
     *
     * @param id  (required)
     * @param cardStatus (required)
     * @return Статус обновлён (status code 200)
     *         or Карта не найдена (status code 404)
     */
    @Operation(
        operationId = "apiAdminCardsIdStatusPatch",
        summary = "Изменить статус карты (активировать/блокировать) (ADMIN)",
        tags = { "Карты (ADMIN)" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Статус обновлён", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = CardDto.class))
    }),
            @ApiResponse(responseCode = "404", description = "Карта не найдена")
        },
        security = {
            @SecurityRequirement(name = "bearerAuth")
        }
    )
    @RequestMapping(
        method = RequestMethod.PATCH,
        value = "/api/admin/cards/{id}/status",
        consumes = { "application/json" }
    )

    ResponseEntity<CardDto> apiAdminCardsIdStatusPatch(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH)
        @PathVariable("id")
        Integer id,

        @Parameter(name = "ApiAdminCardsIdStatusPatchRequest", description = "", required = true)
        @Valid
        @RequestBody CardStatusChange
            cardStatus
    );

    /**
     * POST /users : Создать нового пользователя (ADMIN)
     *
     * @param userCreateRequest  (required)
     * @return Пользователь создан (status code 201)
     *         or Доступ запрещён (не ADMIN) (status code 403)
     */
    @Operation(
        operationId = "apiAdminUserPost",
        summary = "Создать нового пользователя (ADMIN)",
        tags = { "Пользователи (ADMIN)" },
        responses = {
            @ApiResponse(responseCode = "201", description = "Пользователь создан", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
            }),
            @ApiResponse(responseCode = "403", description = "Доступ запрещён (не ADMIN)")
        },
        security = {
            @SecurityRequirement(name = "bearerAuth")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/api/admin/users",
        produces = { "application/json" },
        consumes = { "application/json" }
    )

    ResponseEntity<UserDto> apiAdminUsersPost(
        @Parameter(name = "UserCreateRequest", description = "", required = true)
        @Valid
        @RequestBody UserCreateDto userCreateRequest
    ) throws Exception;

    /**
     * DELETE /api/admin/users/{id} : Удалить пользователя (ADMIN)
     *
     * @param id  (required)
     * @return Пользователь удален (status code 204)
     *         or Пользователь не найден (status code 404)
     */
    @Operation(
        operationId = "apiAdminUsersIdDelete",
        summary = "Удалить пользователя (ADMIN)",
        tags = { "Пользователи (ADMIN)" },
        responses = {
            @ApiResponse(responseCode = "204", description = "Пользователь удален"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
        },
        security = {
            @SecurityRequirement(name = "bearerAuth")
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/api/admin/users/{id}"
    )

    ResponseEntity<Void> apiAdminUsersIdDelete(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH)
        @PathVariable("id")
        Integer id
    );

    /**
     * POST /api/transfers : Перевод между своими картами (USER)
     *
     * @param transferRequest  (required)
     * @return Перевод выполнен (status code 200)
     *         or Недостаточно средств, неверные данные (status code 400)
     *         or Нет доступа к одной из карт (status code 403)
     */
    @Operation(
        operationId = "apiTransfersPost",
        summary = "Перевод между своими картами (USER)",
        tags = { "Переводы" },
        responses = {
            @ApiResponse(responseCode = "200", description = "Перевод выполнен", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = TransferResponse.class))
            }),
            @ApiResponse(responseCode = "400", description = "Недостаточно средств, неверные данные"),
            @ApiResponse(responseCode = "403", description = "Нет доступа к одной из карт")
        },
        security = {
            @SecurityRequirement(name = "bearerAuth")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/users/{id}/cards/transfers",
        produces = { "application/json" },
        consumes = { "application/json" }
    )ResponseEntity<TransferResponse> apiTransfersPost(
        @PathVariable Integer userId,

        @Parameter(name = "TransferRequest", description = "", required = true)
        @Valid
        @RequestBody TransferRequest transferRequest
    ) throws Exception;
}
